package com.spring.ai.basic.agent;

import java.util.Map;

import org.springframework.ai.chat.client.AdvisorParams;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@Component
public class AgentRouter {
    private final ChatClient chatClient;
    private final String systemPrompt;

    public AgentRouter(ChatClient.Builder chatClient, @Qualifier("workerPrompts") Map<String, String> prompts) {
        this.chatClient = chatClient.defaultAdvisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT).build();
        this.systemPrompt = prompts.get("router");
    }

    public record RoutingResponse(String reasoning, @JsonPropertyDescription("It must be one of: task, mail, shopping, knowledge, rag") String selection){};

    public String route(String userMessage, String userId, String conversationId) {
        try {
            RoutingResponse response = chatClient.prompt()
                .system(systemPrompt)
                .user(userMessage)
                .call()
                .entity(RoutingResponse.class);
            System.out.println("Routing Reasoning: " + response.reasoning());
            System.out.println("Selected Agent: " + response.selection());
            return response.selection(); // task, mail, shopping, knowledge, rag
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error routing user message: " + e.getMessage());
            return "요청처리오류";
        }
    }
}
