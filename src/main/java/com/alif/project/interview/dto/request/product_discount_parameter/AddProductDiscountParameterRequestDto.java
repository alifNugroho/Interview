package com.alif.project.interview.dto.request.product_discount_parameter;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddProductDiscountParameterRequestDto {

    private String productCategory;

    private Double discount;
}
