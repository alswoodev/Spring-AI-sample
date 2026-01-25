package com.spring.ai.basic.service;

import com.spring.ai.basic.dto.SecretaryDTOs;
import com.spring.ai.basic.entity.Task;
import com.spring.ai.basic.entity.enums.task.TaskStatus;
import com.spring.ai.basic.repository.TaskRepository;
import com.spring.ai.basic.repository.UserRepository;
import com.spring.ai.basic.entity.User;

import java.time.LocalDate;
import java.util.List;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TaskServiceTest {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    private String userId;
    private Task task;

    @BeforeEach
    void setUp() {
        // Generate Test User
        User user = User.builder()
                .email("taskService@example.com")
                .name("Test User")
                .build();
        userRepository.save(user);

        userId = userRepository.findAll().get(0).getUserId();

        // Generate Test Task
        task = Task.builder()
                .userId(userId)
                .title("Test Task")
                .description("Test Description")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .type("EVENT")
                .priority("MEDIUM")
                .status(TaskStatus.SCHEDULED)
                .build();
        taskRepository.save(task);
    }

    @Test
    void testGetTaskByUserId() {
        //given
        SecretaryDTOs.UserId userIdDTO = new SecretaryDTOs.UserId(userId);

        //when
        List<SecretaryDTOs.TaskResponse> tasks = taskService.getTaskByUserId(userIdDTO);

        //then
        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).title());
    }

    @Test
    void testGetFutureTaskByUserId() {
        //given
        SecretaryDTOs.UserId userIdDTO = new SecretaryDTOs.UserId(userId);
        Task task2 = Task.builder()
                        .userId(userId)
                        .title("Test Task2")
                        .description("Test Description2")
                        .startDate(LocalDate.now().plusDays(3))
                        .endDate(LocalDate.now().plusDays(7))
                        .build();
        taskRepository.save(task2);

        //when
        List<SecretaryDTOs.TaskResponse> tasks = taskService.getFutureTaskByUserId(userIdDTO);

        //then
        assertEquals(2, tasks.size());
        assertEquals("Test Task", tasks.get(0).title());
    }

    @Test
    void testGetTaskByDateRange() {
        //given
        LocalDate today = LocalDate.now();
        SecretaryDTOs.TaskFindByDateRequest request = new SecretaryDTOs.TaskFindByDateRequest(
                userId,
                today.toString(),
                today.plusDays(1).toString()
        );
        Task task2 = Task.builder()
                        .userId(userId)
                        .title("Test Task2")
                        .description("Test Description2")
                        .startDate(LocalDate.now().plusDays(1))
                        .endDate(LocalDate.now().plusDays(3))
                        .build();
        taskRepository.save(task2);

        //when
        List<SecretaryDTOs.TaskResponse> tasks = taskService.getTask(request);

        //then
        assertEquals(2, tasks.size());
        assertEquals("Test Task", tasks.get(0).title());
    }

    @Test
    void testAddTask() {
        //given
        SecretaryDTOs.TaskAddRequest request = new SecretaryDTOs.TaskAddRequest(
                userId,
                "New Task",
                "New Description",
                LocalDate.now().toString(),
                LocalDate.now().plusDays(2).toString(),
                "MEETING",
                "HIGH"
        );

        //when
        List<SecretaryDTOs.TaskResponse> tasks = taskService.addTask(request); //Vertify Return Value
        List<Task> savedTasks = taskRepository.findByUserUserId(userId); //Vertify Task is really in DB

        //then
        assertEquals(1, tasks.size());
        assertEquals("New Task", tasks.get(0).title());
        
        assertEquals(2, savedTasks.size()); 
    }

    @Test
    void testCancelTask() {
        //given
        SecretaryDTOs.TaskCancleRequest request = new SecretaryDTOs.TaskCancleRequest(
                userId,
                task.getTaskId()
        );

        //when
        List<SecretaryDTOs.TaskResponse> tasks = taskService.cancelTask(request); //Vertify Return Value
        Task cancelledTask = taskRepository.findById(task.getTaskId()).orElseThrow(); //Vertify Task is really in DB

        //then
        assertEquals(1, tasks.size());
        assertEquals(TaskStatus.CANCELLED, tasks.get(0).status());

        assertEquals(TaskStatus.CANCELLED, cancelledTask.getStatus());
    }

    @Test
    void testGetTaskWithPriority() {
        //given
        SecretaryDTOs.TaskFindByPriorityRequest request = new SecretaryDTOs.TaskFindByPriorityRequest(
                userId,
                "HIGH"
        );
        Task task2 = Task.builder()
                        .userId(userId)
                        .title("Test Task2")
                        .description("Test Description2")
                        .startDate(LocalDate.now().plusDays(3))
                        .endDate(LocalDate.now().plusDays(7))
                        .priority("HIGH")
                        .build();
        taskRepository.save(task2);

        //when
        List<SecretaryDTOs.TaskResponse> tasks = taskService.getTaskWithPriority(request);

        //then
        assertEquals(1, tasks.size());
        assertEquals("Test Task2", tasks.get(0).title());
    }
}
