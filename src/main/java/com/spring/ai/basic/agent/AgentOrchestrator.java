package com.spring.ai.basic.agent;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentOrchestrator {
    private final AgentRouter agentRouter;
    private final TaskAgent taskAgent;
    private final MailAgent mailAgent;
    private final ShoppingAgent shoppingAgent;
    private final KnowledgeManagerAgent knowledgeManagerAgent;
    private final RagAgent ragAgent;

    public String chat(String userMessage, String userId, String conversationId) {
        String selectedAgent = agentRouter.route(userMessage, userId, conversationId);
        return switch (selectedAgent) {
            case "task" -> taskAgent.process(userMessage, userId, conversationId);
            case "mail" -> mailAgent.process(userMessage, userId, conversationId);
            case "shopping" -> shoppingAgent.process(userMessage, userId, conversationId);
            case "knowledge" -> knowledgeManagerAgent.process(userMessage, userId, conversationId);
            case "rag" -> ragAgent.process(userMessage, userId, conversationId);
            default -> knowledgeManagerAgent.process(userMessage, userId, conversationId);
        };

    }
}
