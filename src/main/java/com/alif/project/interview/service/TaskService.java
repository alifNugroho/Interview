package com.alif.project.interview.service;

import com.alif.project.interview.dto.request.task.AddTaskRequestDto;
import com.alif.project.interview.dto.request.task.CompletedTaskRequestDto;
import com.alif.project.interview.dto.request.task.DeletedTaskRequestDto;
import com.alif.project.interview.dto.response.task.AddTaskResponseDto;
import com.alif.project.interview.dto.response.task.CompletedTaskResponseDto;
import com.alif.project.interview.dto.response.task.TaskListResponseDto;
import com.alif.project.interview.exceptions.TaskException;

import java.util.List;

public interface TaskService {

    public AddTaskResponseDto addTask(AddTaskRequestDto addTaskRequestDto) throws TaskException;

    public CompletedTaskResponseDto completedTask(CompletedTaskRequestDto completedTaskRequestDto) throws TaskException;

    public String deletedTask(DeletedTaskRequestDto deletedTaskRequestDto) throws TaskException;

    public List<TaskListResponseDto> getAllNotCompletedTask() throws TaskException;
}
