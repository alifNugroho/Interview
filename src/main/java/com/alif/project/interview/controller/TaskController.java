package com.alif.project.interview.controller;

import com.alif.project.interview.dto.request.task.AddTaskRequestDto;
import com.alif.project.interview.dto.request.task.CompletedTaskRequestDto;
import com.alif.project.interview.dto.request.task.DeletedTaskRequestDto;
import com.alif.project.interview.dto.response.task.AddTaskResponseDto;
import com.alif.project.interview.dto.response.task.CompletedTaskResponseDto;
import com.alif.project.interview.dto.response.task.TaskListResponseDto;
import com.alif.project.interview.exceptions.TaskException;
import com.alif.project.interview.service.TaskService;
import com.alif.project.interview.util.ApiResponseTemplate;
import com.alif.project.interview.util.ApiTemplateMessage;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final ApiTemplateMessage templateMessage;

    private final TaskService taskService;

    public TaskController(ApiTemplateMessage templateMessage,
                          TaskService taskService) {
        this.templateMessage = templateMessage;
        this.taskService = taskService;
    }

    @PostMapping(value = "/addTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addTask(@RequestBody AddTaskRequestDto request) throws TaskException {
        AddTaskResponseDto result = taskService.addTask(request);
        return templateMessage.buildResponseEntity(new ApiResponseTemplate(true, result));
    }

    @PostMapping(value = "/completedTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> completedTask(@Valid @RequestBody CompletedTaskRequestDto request) throws TaskException {
        CompletedTaskResponseDto result = taskService.completedTask(request);
        return templateMessage.buildResponseEntity(new ApiResponseTemplate(true, result));
    }

    @PostMapping(value = "/deletedTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deletedTask(@Valid @RequestBody DeletedTaskRequestDto request) throws TaskException {
        String result = taskService.deletedTask(request);
        return templateMessage.buildResponseEntity(new ApiResponseTemplate(true, result));
    }

    @GetMapping(value = "/getAllNotCompletedTask")
    public ResponseEntity<Object> getAllNotCompletedTask() throws TaskException {
        List<TaskListResponseDto> result = taskService.getAllNotCompletedTask();
        return templateMessage.buildResponseEntity(new ApiResponseTemplate(true, result));
    }
}
