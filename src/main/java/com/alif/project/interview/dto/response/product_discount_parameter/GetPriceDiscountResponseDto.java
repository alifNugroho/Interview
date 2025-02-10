package com.alif.project.interview.dto.response.product_discount_parameter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetPriceDiscountResponseDto {

    private String productCategory;

    private double discount;

    private double price;
}
