package com.alif.project.interview.service.impl;

import com.alif.project.interview.dto.request.task.AddTaskRequestDto;
import com.alif.project.interview.dto.request.task.CompletedTaskRequestDto;
import com.alif.project.interview.dto.request.task.DeletedTaskRequestDto;
import com.alif.project.interview.dto.response.task.AddTaskResponseDto;
import com.alif.project.interview.dto.response.task.CompletedTaskResponseDto;
import com.alif.project.interview.dto.response.task.TaskListResponseDto;
import com.alif.project.interview.exceptions.TaskException;
import com.alif.project.interview.model.Task;
import com.alif.project.interview.repository.TaskRepository;
import com.alif.project.interview.service.TaskService;
import com.alif.project.interview.util.Constant;
import com.alif.project.interview.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Override
    public AddTaskResponseDto addTask(AddTaskRequestDto addTaskRequestDto) throws TaskException {

        // Set Data From Request to Model
        Task task = new Task();
        try {

            task.setTaskName(Util.checkRequest(addTaskRequestDto.getTaskName(), "Task Name"));
            task.setStartDate(checkRequestDate(addTaskRequestDto.getStartDate(), "Start Date"));
            task.setEndDate(checkRequestDate(addTaskRequestDto.getEndDate(), "End Date"));
            task.setDueDate(checkRequestDate(addTaskRequestDto.getDueDate(), "Due Date"));
            task.setTaskDescription(Util.checkRequest(addTaskRequestDto.getTaskDescription(), "Task Description"));
            task.setStatusTask(0);

            // Save Model to Database with JPA
            taskRepository.save(task);

        } catch (TaskException exception) {
            throw new TaskException(exception.getMessage());
        }

        // Return Result after save
        return AddTaskResponseDto.builder()
                .id(task.getId())
                .taskName(task.getTaskName())
                .startDate(dateToString(task.getStartDate()))
                .endDate(dateToString(task.getEndDate()))
                .dueDate(dateToString(task.getDueDate()))
                .taskDescription(task.getTaskDescription())
                .statusTask(Util.statusTask(task.getStatusTask()))
                .build();
    }

    @Override
    public CompletedTaskResponseDto completedTask(CompletedTaskRequestDto completedTaskRequestDto) throws TaskException{

        Task task = new Task();
        try {

            task = taskRepository.findById(checkRequestLong(completedTaskRequestDto.getId(), "Id")).isPresent() ?
                    taskRepository.findById(checkRequestLong(completedTaskRequestDto.getId(), "Id")).get() : null;

            if(task == null) {

                throw new TaskException(Constant.TASK_NOT_FOUND);
            }
            task.setStatusTask(1);
            taskRepository.save(task);

        } catch (Exception exception) {
            throw new TaskException(exception.getMessage());
        }

        // Return Result after completed task
        return CompletedTaskResponseDto.builder()
                .id(task.getId())
                .taskName(task.getTaskName())
                .startDate(dateToString(task.getStartDate()))
                .endDate(dateToString(task.getEndDate()))
                .dueDate(dateToString(task.getDueDate()))
                .taskDescription(task.getTaskDescription())
                .statusTask(Util.statusTask(task.getStatusTask()))
                .build();
    }

    @Override
    public String deletedTask(DeletedTaskRequestDto deletedTaskRequestDto) throws TaskException {

        Task task = new Task();

        try {

            task = taskRepository.findById(checkRequestLong(deletedTaskRequestDto.getId(), "Id")).isPresent() ?
                    taskRepository.findById(checkRequestLong(deletedTaskRequestDto.getId(), "Id")).get() : null;

            if (task == null) {

                throw new TaskException(Constant.TASK_NOT_FOUND);
            }
            taskRepository.delete(task);
        } catch (Exception exception) {
            throw new TaskException(exception.getMessage());
        }

        return Constant.SUCCES_DELETE;
    }

    @Override
    public List<TaskListResponseDto> getAllNotCompletedTask() throws TaskException {

        List<TaskListResponseDto> taskListResponseDtoList = new ArrayList<>();

        try {

            List<Task> taskList = taskRepository.findBystatusTaskNotCompleted();

            if(taskList.isEmpty()) {

                throw new TaskException(Constant.TASK_LIST_NOT_FOUND);
            }
            for(Task task : taskList) {

                taskListResponseDtoList.add(TaskListResponseDto.builder()
                        .id(task.getId())
                        .taskName(task.getTaskName())
                        .startDate(dateToString(task.getStartDate()))
                        .endDate(dateToString(task.getEndDate()))
                        .dueDate(dateToString(task.getDueDate()))
                        .taskDescription(task.getTaskDescription())
                        .statusTask(Util.statusTask(task.getStatusTask()))
                        .build());
            }
        } catch (Exception exception) {

            throw new TaskException(Constant.TASK_LIST_FAILED);
        }

        return taskListResponseDtoList;
    }

    public Date checkRequestDate(String input, String name) throws TaskException {
        Date date = new Date();
        if(input == null) {
            throw new TaskException(name+" Cannot be Null");
        }
        try {
            date = new SimpleDateFormat("dd-MM-yyyy").parse(input);
        } catch (ParseException exception) {
            throw new TaskException(name+" Must Be format dd-MM-yyyy");
        }
        return date;
    }

    public Long checkRequestLong(Long input, String name) throws TaskException {
        Long result = 0L;
        if(input == null) {
            throw new TaskException(name+" Cannot be Null");
        }
        else {
            result = input;
        }
        return result;
    }

    public String dateToString(Date input) {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(input);
        return strDate;
    }
}
