package com.alif.project.interview.dto.request.nearest_partner_search;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GetResultRequestDto {

    private int[] input;
}
