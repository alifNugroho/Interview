package com.alif.project.interview.dto.response.task;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class AddTaskResponseDto {

    private Long id;

    private String taskName;

    private String startDate;

    private String endDate;

    private String dueDate;

    private String taskDescription;

    private String statusTask;
}
