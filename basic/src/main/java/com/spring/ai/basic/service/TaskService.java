package com.spring.ai.basic.service;

import org.springframework.stereotype.Service;
import com.spring.ai.basic.dto.SecretaryDTOs;
import com.spring.ai.basic.entity.Task;
import com.spring.ai.basic.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import com.spring.ai.basic.entity.enums.task.TaskStatus;
import java.util.List;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    // Retrieve tasks by user ID
    public List<SecretaryDTOs.TaskResponse> getTaskByUserId(SecretaryDTOs.UserId userIdDto) {
        nullValidate(userIdDto.userId());
        List<Task> tasks = taskRepository.findByUserUserId(userIdDto.userId());
        return mapToTaskResponses(tasks);
    }

    // Retrieve future tasks by user ID
    public List<SecretaryDTOs.TaskResponse> getFutureTaskByUserId(SecretaryDTOs.UserId userIdDto) {
        nullValidate(userIdDto.userId());
        List<Task> tasks = taskRepository.findUpcomingTasks(userIdDto.userId());
        return mapToTaskResponses(tasks);
    }

    // Retrieve tasks by date range
    public List<SecretaryDTOs.TaskResponse> getTask(SecretaryDTOs.TaskFindByDateRequest request) {
        nullValidate(request.userId());
        LocalDate startDate = LocalDate.parse(request.startDate());
        LocalDate endDate = LocalDate.parse(request.endDate());
        if(startDate.isAfter(endDate)){
            throw new IllegalArgumentException("Invalid date range: 'from' date is after 'to' date");
        }
        List<Task> tasks = taskRepository.findTask(request.userId(), startDate, endDate, TaskStatus.SCHEDULED);
        return mapToTaskResponses(tasks);
    }

    // Retrieve tasks by priority
    public List<SecretaryDTOs.TaskResponse> getTaskWithPriority(SecretaryDTOs.TaskFindByPriorityRequest request) {
        nullValidate(request.userId());
        List<Task> tasks = taskRepository.findTaskWithPriority(request.userId(), request.priority(), TaskStatus.SCHEDULED);
        return mapToTaskResponses(tasks);
    }

    // Add a new task
    public List<SecretaryDTOs.TaskResponse> addTask(SecretaryDTOs.TaskAddRequest request) {
        nullValidate(request.userId());
        LocalDate startDate = LocalDate.parse(request.startDate());
        LocalDate endDate = LocalDate.parse(request.endDate());
        if(startDate.isAfter(endDate)){
            throw new IllegalArgumentException("Invalid date range: 'from' date is after 'to' date");
        }
        Task task = Task.createTask(request.userId(), request.title(), request.description(), startDate, endDate, request.type(), request.priority());
        return mapToTaskResponses(List.of(taskRepository.save(task)));
    }

    // Cancel a task
    public List<SecretaryDTOs.TaskResponse> cancelTask(SecretaryDTOs.TaskCancleRequest request) {
        nullValidate(request.userId());
        // Implementation for cancelling a task
        Task task = taskRepository.findById(request.taskId()).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        if (!task.getUserId().equals(request.userId())) {
            throw new IllegalArgumentException("User does not have permission to cancel this task");
        }
        task.setStatus(TaskStatus.CANCELLED);
        taskRepository.save(task);
        return mapToTaskResponses(List.of(task));
    }

    // Helper method to map Task entities to TaskResponse DTOs
    private List<SecretaryDTOs.TaskResponse> mapToTaskResponses(List<Task> tasks) {
        return tasks.stream()
                .map(task -> new SecretaryDTOs.TaskResponse(
                        task.getTaskId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStartDate().toString(),
                        task.getEndDate().toString(),
                        task.getType(),
                        task.getPriority(),
                        task.getStatus()
                ))
                .toList();
    }

    // Dummy implementation for user validation
    private void nullValidate(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID is required");
        }
    }
}