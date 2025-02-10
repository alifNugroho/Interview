package com.alif.project.interview.service;

import com.alif.project.interview.dto.request.product_discount_parameter.AddProductDiscountParameterRequestDto;
import com.alif.project.interview.dto.request.product_discount_parameter.GetPriceDiscountRequestDto;
import com.alif.project.interview.dto.response.product_discount_parameter.AddProductDiscountParameterResponseDto;
import com.alif.project.interview.dto.response.product_discount_parameter.GetPriceDiscountResponseDto;
import com.alif.project.interview.exceptions.TaskException;

public interface ProductDiscountParameterService {

    public AddProductDiscountParameterResponseDto addDiscountParameter(
            AddProductDiscountParameterRequestDto addProductDiscountParameterRequestDto
    )throws TaskException;

    public GetPriceDiscountResponseDto getPriceDiscount(GetPriceDiscountRequestDto getPriceDiscountRequestDto) throws TaskException;

}
