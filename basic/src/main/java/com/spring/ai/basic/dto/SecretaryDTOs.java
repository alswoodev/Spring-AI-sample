package com.spring.ai.basic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.spring.ai.basic.entity.enums.task.TaskStatus;
import java.util.List;

public class SecretaryDTOs {

    public record AccessToken(
        @JsonProperty(required = true)
        @JsonPropertyDescription("Access token for authentication")
        String accessToken
    ){}

    public record UserId(
        @JsonProperty(required = true)
        @JsonPropertyDescription("ID of the user")
        String userId
    ){}

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
        @JsonProperty(required = true)
        @JsonPropertyDescription("ID of the user")
        String userId,
        @JsonProperty(required = true)
        @JsonPropertyDescription("Start date in ISO 8601 format of YYYY-MM-DD")
        String startDate,
        @JsonProperty(required = true)
        @JsonPropertyDescription("End date in ISO 8601 format of YYYY-MM-DD")
        String endDate
    ){}

    public record TaskFindByPriorityRequest(
        @JsonProperty(required = true)
        @JsonPropertyDescription("ID of the user")
        String userId,
        @JsonProperty(required = true)
        @JsonPropertyDescription("Priority level of the task (e.g., HIGH, MEDIUM, LOW)")
        String priority
    ){}

    public record TaskAddRequest(
        @JsonProperty(required = true)
        @JsonPropertyDescription("ID of the user")
        String userId,
        @JsonProperty(required = true)
        @JsonPropertyDescription("Description of the task to be added")
        String title,
        @JsonProperty(required = true)
        @JsonPropertyDescription("Detailed information about the task")
        String description,
        @JsonProperty(required = true)
        @JsonPropertyDescription("Start date and time of the task in ISO 8601 format of YYYY-MM-DD")
        String startDate,
        @JsonProperty(required = true)
        @JsonPropertyDescription("End date and time of the task in ISO 8601 format of YYYY-MM-DD")
        String endDate,
        @JsonProperty(required = false)
        @JsonPropertyDescription("Type of the task (e.g., meeting, reminder, event)")
        String type,
        @JsonProperty(required = false)
        @JsonPropertyDescription("Priority level of the task (e.g., HIGH, MEDIUM, LOW)")
        String priority
    ){}

    public record TaskCancleRequest(
        @JsonProperty(required = true)
        @JsonPropertyDescription("ID of the user")
        String userId,
        @JsonProperty(required = true)
        @JsonPropertyDescription("ID of the task to be deleted")
        Long taskId
    ){}

    public record ShoppingItemAddRequest(
        @JsonProperty(required = true)
        @JsonPropertyDescription("ID of the user")
        String userId,
        @JsonProperty(required = true)
        @JsonPropertyDescription("Name of the shopping item to be added")
        String itemName,
        @JsonProperty(required = false)
        @JsonPropertyDescription("Quantity of the shopping item")
        String quantity,
        @JsonProperty(required = false)
        @JsonPropertyDescription("Additional notes about the shopping item")
        String notes
    ){}

    public record ShoppingItemCancleRequest(
        @JsonProperty(required = true)
        @JsonPropertyDescription("ID of the user")
        String userId,
        @JsonProperty(required = true)
        @JsonPropertyDescription("ID of the shopping item to be cancelled")
        String itemId
    ){}

    public record ShoppingItemPurchaseRequest(
        @JsonProperty(required = true)
        @JsonPropertyDescription("ID of the user")
        String userId,
        @JsonProperty(required = true)
        @JsonPropertyDescription("Date when the shopping items were purchased")
        String purchasedAt
    ){}

    public record ShpopingItemSome(
        @JsonProperty(required = true)
        @JsonPropertyDescription("ID of the user")
        String userId,
        @JsonProperty(required = true)
        @JsonPropertyDescription("Date when the shopping items were purchased")
        String purchasedAt,
        @JsonProperty(required = true)
        @JsonPropertyDescription("List of item IDs to mark as purchased")
        List<String> itemIds
    ){}

    public record ShpopingItemExcept(
        @JsonProperty(required = true)
        @JsonPropertyDescription("ID of the user")
        String userId,
        @JsonProperty(required = true)
        @JsonPropertyDescription("Date when the shopping items were purchased")
        String purchasedAt,
        @JsonProperty(required = true)
        @JsonPropertyDescription("List of item IDs that should remain pending")
        List<String> itemIds
    ){}

    public record ShoppingItemResponse(
        @JsonProperty(required = true)
        @JsonPropertyDescription("ID of Shopping item")
        String itemId,
        @JsonProperty(required = true)
        @JsonPropertyDescription("Name of the shopping item to be added")
        String itemName,
        @JsonProperty(required = false)
        @JsonPropertyDescription("Quantity of the shopping item")
        String quantity
    ){}
}
