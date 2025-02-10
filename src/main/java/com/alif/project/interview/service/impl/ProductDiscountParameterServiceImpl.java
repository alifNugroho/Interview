package com.alif.project.interview.service.impl;

import com.alif.project.interview.dto.request.product_discount_parameter.AddProductDiscountParameterRequestDto;
import com.alif.project.interview.dto.request.product_discount_parameter.GetPriceDiscountRequestDto;
import com.alif.project.interview.dto.response.product_discount_parameter.AddProductDiscountParameterResponseDto;
import com.alif.project.interview.dto.response.product_discount_parameter.GetPriceDiscountResponseDto;
import com.alif.project.interview.exceptions.TaskException;
import com.alif.project.interview.model.ProductDiscountParameter;
import com.alif.project.interview.repository.ProductDiscountParameterRepository;
import com.alif.project.interview.service.ProductDiscountParameterService;
import com.alif.project.interview.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDiscountParameterServiceImpl implements ProductDiscountParameterService {

    @Autowired
    ProductDiscountParameterRepository productDiscountParameterRepository;

    @Override
    public AddProductDiscountParameterResponseDto addDiscountParameter(AddProductDiscountParameterRequestDto addProductDiscountParameterRequestDto) {

        ProductDiscountParameter productDiscountParameter = new ProductDiscountParameter();
        try {
            productDiscountParameter.setProductCategory(Util.checkRequest(addProductDiscountParameterRequestDto.getProductCategory(), "Product Category"));
            productDiscountParameter.setDiscount(checkRequestDouble(addProductDiscountParameterRequestDto.getDiscount(), "Discount"));

            productDiscountParameterRepository.save(productDiscountParameter);
        } catch (TaskException exception) {

            throw new TaskException(exception.getMessage());

        }


        return AddProductDiscountParameterResponseDto.builder()
                .id(productDiscountParameter.getId())
                .productCategory(productDiscountParameter.getProductCategory())
                .discount(productDiscountParameter.getDiscount())
                .build();
    }

    @Override
    public GetPriceDiscountResponseDto getPriceDiscount(GetPriceDiscountRequestDto getPriceDiscountRequestDto) {

        GetPriceDiscountResponseDto getPriceDiscountResponseDto = new GetPriceDiscountResponseDto();
        try {
            List<ProductDiscountParameter> productDiscountParameterList =
                    productDiscountParameterRepository.findAll();

            for(ProductDiscountParameter productDiscountParameter : productDiscountParameterList) {

                if(productDiscountParameter.getProductCategory().equals(getPriceDiscountRequestDto.getProductCategory())) {

                    getPriceDiscountResponseDto.setProductCategory(Util.checkRequest(productDiscountParameter.getProductCategory(), "Product Category"));
                    getPriceDiscountResponseDto.setDiscount(productDiscountParameter.getDiscount());
                    getPriceDiscountResponseDto.setPrice(calculatePriceDiscount(checkRequestDouble(getPriceDiscountRequestDto.getPrice(), "Price"), productDiscountParameter.getDiscount()));

                    return getPriceDiscountResponseDto;
                }
            }
        } catch (TaskException exception){

        }

        getPriceDiscountResponseDto.setProductCategory(getPriceDiscountRequestDto.getProductCategory());
        getPriceDiscountResponseDto.setDiscount(0);
        getPriceDiscountResponseDto.setPrice(getPriceDiscountRequestDto.getPrice());
        return getPriceDiscountResponseDto;
    }

    public double calculatePriceDiscount(double price, double discount){

        double result = price * discount;

        return result;
    }

    public Double checkRequestDouble(Double input, String name) throws TaskException {
        Double result = 0.0;
        if(input == null) {
            throw new TaskException(name+" Cannot be Null");
        }
        else {
            result = input;
        }
        return result;
    }
}
