package com.alif.project.interview.dto.request.task;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
public class AddTaskRequestDto {

//    @NotNull(message = "The Task Name is required.")
    private String taskName;

//    @NotNull(message = "The Start Date is required.")
    private String startDate;

//    @NotNull(message = "The End Date is required.")
    private String endDate;

//    @NotNull(message = "The Due Date is required.")
    private String dueDate;

//    @NotNull(message = "The Task Description is required.")
    private String taskDescription;

}
