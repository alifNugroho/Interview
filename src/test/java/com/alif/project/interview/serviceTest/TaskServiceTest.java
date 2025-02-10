package com.alif.project.interview.serviceTest;

import com.alif.project.interview.dto.request.task.AddTaskRequestDto;
import com.alif.project.interview.dto.response.task.AddTaskResponseDto;
import com.alif.project.interview.model.Task;
import com.alif.project.interview.repository.TaskRepository;
import com.alif.project.interview.service.impl.TaskServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void TaskService_AddTask_ReturnAddTaskResponseDto() {
        Task task = new Task();
        task.setTaskName("Development");
        task.setStartDate(new Date());
        task.setEndDate(new Date());
        task.setDueDate(new Date());
        task.setTaskDescription("Development wab app");
        task.setStatusTask(0);

        AddTaskRequestDto addTaskRequestDto = new AddTaskRequestDto();
        addTaskRequestDto.setTaskName("Development");
        addTaskRequestDto.setStartDate(new Date());
        addTaskRequestDto.setEndDate(new Date());
        addTaskRequestDto.setDueDate(new Date());
        addTaskRequestDto.setTaskDescription("Development wab app");


        when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);

        AddTaskResponseDto addTaskResponseDto = taskService.addTask(addTaskRequestDto);

        Assertions.assertThat(addTaskResponseDto).isNotNull();
    }
}
