package com.alif.project.interview.dto.request.product_discount_parameter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPriceDiscountRequestDto {

    private String productCategory;

    private Double price;
}
