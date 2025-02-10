package com.alif.project.interview.dto.request.task;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DeletedTaskRequestDto {

    @NotNull(message = "The Id is required.")
    private Long id;
}
