package com.spring.ai.basic.tool;

import com.spring.ai.basic.dto.SecretaryDTOs;
import com.spring.ai.basic.entity.Task;
import com.spring.ai.basic.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class TaskTool {

    private final TaskService taskService;

    public TaskTool(@Autowired TaskService taskService) {
        this.taskService = taskService;
    }

    @Tool(description = "고객의 id를 바탕으로 일정을 추가합니다")
    public SecretaryDTOs.TaskResponse addTask(SecretaryDTOs.TaskAddRequest request){
        LocalDate startDate;
        LocalDate endDate;

        try{
            startDate = LocalDate.parse(request.startDate());
            endDate = LocalDate.parse(request.endDate());
        }
        catch(DateTimeParseException e){
            throw new IllegalArgumentException("startDate and endDate must be in 'yyyy-MM-dd' format (e.g., 2024-01-31).");
        }

        Task task = taskService.addTask(request.userId(), request.title(), request.description(), startDate, endDate, request.type(), request.priority());
        return new SecretaryDTOs.TaskResponse(
                            task.getTaskId(),
                            task.getTitle(),
                            task.getDescription(),
                            task.getStartDate().toString(),
                            task.getEndDate().toString(),
                            task.getType(),
                            task.getPriority(),
                            task.getStatus());
    }

    @Tool(description = "고객의 id와 우선순위를 바탕으로 일정을 조회합니다")
    public List<SecretaryDTOs.TaskResponse> getTaskWithPriority(SecretaryDTOs.TaskFindByPriorityRequest request){
        return mapToTaskResponses(taskService.getTaskWithPriority(request.userId(), request.priority()));
    }

    @Tool(description = "startDate와 endDate사이에 시작 예정인 일정을 알려줍니다. startDate와 endDate를 반드시 포함하고, YYYY-MM-DD형식을 따르세요")
    public List<SecretaryDTOs.TaskResponse> getTask(SecretaryDTOs.TaskFindByDateRequest request){
        LocalDate startDate;
        LocalDate endDate;

        try{
            startDate = LocalDate.parse(request.startDate());
            endDate = LocalDate.parse(request.endDate());
        }
        catch(DateTimeParseException e){
            throw new IllegalArgumentException("startDate and endDate must be in 'yyyy-MM-dd' format (e.g., 2024-01-31).");
        }

        return mapToTaskResponses(taskService.getTask(request.userId(), startDate, endDate));
    }
   
    @Tool(description = "고객 id를 바탕으로 현재 날짜 혹은 미래의 날짜에 예정된 일정을 모두 조회합니다")
    public List<SecretaryDTOs.TaskResponse> getFutureTask(SecretaryDTOs.UserId userIdDto){
        return mapToTaskResponses(taskService.getFutureTaskByUserId(userIdDto.userId()));
    }

    @Tool(description = "일정 id를 바탕으로 해당 일정을 삭제합니다. 일정 id를 구하기 위해서는, 다른 도구를 이용하여서 일정 id를 얻어야 합니다")
    public SecretaryDTOs.TaskResponse cancleTask(SecretaryDTOs.TaskCancleRequest request){
        Task task = taskService.cancelTask(request.userId(), request.taskId());
        return new SecretaryDTOs.TaskResponse(
                            task.getTaskId(),
                            task.getTitle(),
                            task.getDescription(),
                            task.getStartDate().toString(),
                            task.getEndDate().toString(),
                            task.getType(),
                            task.getPriority(),
                            task.getStatus());
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
}