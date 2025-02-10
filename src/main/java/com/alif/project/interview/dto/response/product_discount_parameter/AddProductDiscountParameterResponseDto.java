package com.alif.project.interview.dto.response.product_discount_parameter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddProductDiscountParameterResponseDto {

    private  Long id;

    private String productCategory;

    private Double discount;
}
