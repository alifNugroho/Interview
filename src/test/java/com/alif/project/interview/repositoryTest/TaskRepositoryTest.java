package com.alif.project.interview.repositoryTest;

import com.alif.project.interview.model.Task;
import com.alif.project.interview.repository.TaskRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;


    @Test
    public void TaskRepository_Save_ReturnSaveTask () {
        Task task = new Task();
        task.setTaskName("Development");
        task.setStartDate(new Date());
        task.setEndDate(new Date());
        task.setDueDate(new Date());
        task.setTaskDescription("Development wab app");
        task.setStatusTask(0);


        Task saveTask = taskRepository.save(task);

        //Assert
        Assertions.assertThat(saveTask).isNotNull();
        Assertions.assertThat(saveTask.getId()).isGreaterThan(0);
    }

    @Test
    public void TaskRepository_findById_ReturnTask () {
        Task task = new Task();
        task.setTaskName("Development1");
        task.setStartDate(new Date());
        task.setEndDate(new Date());
        task.setDueDate(new Date());
        task.setTaskDescription("Development1 wab app");
        task.setStatusTask(0);


        Task saveTask = taskRepository.save(task);

        Task pokemon = taskRepository.findById(task.getId()).get();

        Assertions.assertThat(pokemon).isNotNull();
    }

    @Test
    public void TaskRepository_findBystatusTaskNotCompleted_ReturnMoreThenOneTask () {
        Task task = new Task();
        task.setTaskName("Development2");
        task.setStartDate(new Date());
        task.setEndDate(new Date());
        task.setDueDate(new Date());
        task.setTaskDescription("Development1 wab app");
        task.setStatusTask(0);
        Task saveTask = taskRepository.save(task);

        Task task2 = new Task();
        task2.setTaskName("Development3");
        task2.setStartDate(new Date());
        task2.setEndDate(new Date());
        task2.setDueDate(new Date());
        task2.setTaskDescription("Development1 wab app");
        task2.setStatusTask(0);
        Task saveTask2 = taskRepository.save(task2);

        List<Task> taskList = taskRepository.findBystatusTaskNotCompleted();

        Assertions.assertThat(taskList).isNotNull();
        Assertions.assertThat(taskList.size()).isEqualTo(2);
    }

    @Test
    public void TaskRepository_TaskDelete_ReturnTaskIsEmpty() {
        Task task = new Task();
        task.setTaskName("Development4");
        task.setStartDate(new Date());
        task.setEndDate(new Date());
        task.setDueDate(new Date());
        task.setTaskDescription("Development2 wab app");
        task.setStatusTask(0);

        taskRepository.save(task);

        taskRepository.delete(task);
        Optional<Task> pokemonReturn = taskRepository.findById(task.getId());

        Assertions.assertThat(pokemonReturn).isEmpty();
    }
}
