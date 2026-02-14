package com.spring.ai.basic.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class KnowledgeManageToolDTO {

    public record Request(
        @JsonProperty(required = true)
        @JsonPropertyDescription("The unique identifier of the user who owns this knowledge.")
        String userId,

        @JsonProperty(required = true)
        @JsonPropertyDescription(
            "A list of independent factual statements extracted from user input. " +
            "Each entry should represent a single fact, rule, definition, or procedure."
        )
        List<String> texts
    ) {}

    public record Response(
        @JsonPropertyDescription("Indicates whether the knowledge was successfully stored or updated.")
        boolean success
    ) {}
}