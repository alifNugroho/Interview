package com.alif.project.interview.controller;

import com.alif.project.interview.dto.request.product_discount_parameter.AddProductDiscountParameterRequestDto;
import com.alif.project.interview.dto.request.product_discount_parameter.GetPriceDiscountRequestDto;
import com.alif.project.interview.dto.response.product_discount_parameter.AddProductDiscountParameterResponseDto;
import com.alif.project.interview.dto.response.product_discount_parameter.GetPriceDiscountResponseDto;
import com.alif.project.interview.exceptions.TaskException;
import com.alif.project.interview.service.ProductDiscountParameterService;
import com.alif.project.interview.util.ApiResponseTemplate;
import com.alif.project.interview.util.ApiTemplateMessage;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/priceDiscount")
public class ProductDiscountController {

    private final ApiTemplateMessage templateMessage;

    private final ProductDiscountParameterService productDiscountParameterService;

    public ProductDiscountController(ApiTemplateMessage templateMessage,
                                     ProductDiscountParameterService productDiscountParameterService) {
        this.templateMessage = templateMessage;
        this.productDiscountParameterService = productDiscountParameterService;
    }

    @PostMapping(value = "/addDiscountParameter", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addDiscountParameter(@Valid @RequestBody AddProductDiscountParameterRequestDto request) throws TaskException {
        AddProductDiscountParameterResponseDto result = productDiscountParameterService.addDiscountParameter(request);
        return templateMessage.buildResponseEntity(new ApiResponseTemplate(true, result));
    }

    @PostMapping(value = "/getPriceDiscount", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getPriceDiscount(@Valid @RequestBody GetPriceDiscountRequestDto request) throws TaskException {
        GetPriceDiscountResponseDto result = productDiscountParameterService.getPriceDiscount(request);
        return templateMessage.buildResponseEntity(new ApiResponseTemplate(true, result));
    }
}
