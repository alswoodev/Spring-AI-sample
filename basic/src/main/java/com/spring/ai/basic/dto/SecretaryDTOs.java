package com.spring.ai.basic.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import com.spring.ai.basic.entity.enums.task.TaskStatus;
import java.util.List;

public class SecretaryDTOs {
    public record AccessToken(
        @JsonPropertyDescription("Access token for authentication")
        String accessToken
    ){}

    public record UserId(
        @JsonPropertyDescription("ID of the user")
        String userId
    ) {}

    public record TaskResponse(
        @JsonPropertyDescription("ID of the task")
        Long taskId,
        @JsonPropertyDescription("Title of the task")
        String title,
        @JsonPropertyDescription("Description of the task")
        String description,
        @JsonPropertyDescription("Start date of the task in ISO 8601 format of YYYY-MM-DD")
        String startDate,
        @JsonPropertyDescription("End date of the task in ISO 8601 format of YYYY-MM-DD")
        String endDate,
        @JsonPropertyDescription("Type of the task (e.g., meeting, reminder, event)")
        String type,
        @JsonPropertyDescription("Priority level of the task (e.g., high, medium, low)")
        String priority,
        @JsonPropertyDescription("Status of the task (e.g., SCHEDULED, COMPLETED, CANCELLED)")
        TaskStatus status
    ){}

    public record TaskFindByDateRequest(
        @JsonPropertyDescription("Required: ID of the user")
        String userId,

        @JsonPropertyDescription("Required: Start date in ISO 8601 format of YYYY-MM-DD")
        String startDate,

        @JsonPropertyDescription("Required: End date in ISO 8601 format of YYYY-MM-DD")
        String endDate
    ){}

    public record TaskFindByPriorityRequest(
        @JsonPropertyDescription("Required: ID of the user")
        String userId,
        @JsonPropertyDescription("Required: Priority level of the task (e.g., HIGH, MEDIUM, LOW)")
        String priority
    ){}

    public record TaskAddRequest(
        @JsonPropertyDescription("Required: ID of the user")
        String userId,
        @JsonPropertyDescription("Required: Description of the task to be added")
        String title,
        @JsonPropertyDescription("Required: Detailed information about the task")
        String description,
        @JsonPropertyDescription("Required: Start date and time of the task in ISO 8601 format of YYYY-MM-DD")
        String startDate,
        @JsonPropertyDescription("Required: End date and time of the task in ISO 8601 format of YYYY-MM-DD")
        String endDate,
        @JsonPropertyDescription("Optional: Type of the task (e.g., meeting, reminder, event)")
        String type,
        @JsonPropertyDescription("Optional: Priority level of the task (e.g., HIGH, MEDIUM, LOW)")
        String priority
    ){}

    public record TaskCancleRequest(
        @JsonPropertyDescription("Required: ID of the user")
        String userId,

        @JsonPropertyDescription("Required: ID of the task to be deleted")
        Long taskId
    ){}

    public record ShoppingItemAddRequest(
        @JsonPropertyDescription("Required: ID of the user")
        String userId,
        @JsonPropertyDescription("Required: Name of the shopping item to be added")
        String itemName,
        @JsonPropertyDescription("Optional: Quantity of the shopping item")
        String quantity,
        @JsonPropertyDescription("Optional: Additional notes about the shopping item")
        String notes
    ){}

    public record ShoppingItemCancleRequest(
        @JsonPropertyDescription("Required: ID of the user")
        String userId,

        @JsonPropertyDescription("Required: ID of the shopping item to be cancelled")
        String itemId
    ){}

    public record ShoppingItemPurchaseRequest(
        @JsonPropertyDescription("Required: ID of the user")
        String userId,

        @JsonPropertyDescription("Required: Date when the shopping items were purchased")
        String purchasedAt
    ){}

    public record ShpopingItemSome(
        @JsonPropertyDescription("Require: ID of the user")
        String userId,

        @JsonPropertyDescription("Required: Date when the shopping items were purchased")
        String purchasedAt,

        @JsonPropertyDescription("Required: List of item IDs to mark as purchased")
        List<String> itemIds
    ){}

    public record ShpopingItemExcept(
        @JsonPropertyDescription("Require: ID of the user")
        String userId,

        @JsonPropertyDescription("Required: Date when the shopping items were purchased")
        String purchasedAt,

        @JsonPropertyDescription("Required: List of item IDs that should remain pending")
        List<String> itemIds
    ){}

    public record ShoppingItemResponse(
        @JsonPropertyDescription("Required: ID of Shopping item")
        String itemId,
        @JsonPropertyDescription("Required: Name of the shopping item to be added")
        String itemName,
        @JsonPropertyDescription("Optional: Quantity of the shopping item")
        String quantity
    ){}
}
