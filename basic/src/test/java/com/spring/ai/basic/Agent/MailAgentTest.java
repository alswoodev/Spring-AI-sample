package com.spring.ai.basic.Agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.spring.ai.basic.entity.User;
import com.spring.ai.basic.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.spring.ai.basic.agent.MailAgent;

/*
 * WARNING:
 * This test class triggers actual AI responses and is for verifying model behavior only.
 * Do NOT use it for real API calls to avoid consuming your API key or incurring charges.
 */
/*@SpringBootTest
public class MailAgentTest {
    @Autowired
    private MailAgent mailAgent;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
       testUser = User.builder()
                .email("asdf@gmail.com")
                .name("Test User")
                .build();
        userRepository.save(testUser);
    }

    @Test
    public void processTest() {
        //String userMessage = "{your_mail}으로 '안녕하세요'라는 제목과 함께 새해 안부를 묻는 내용의 메일 보내줘";
        String userMessage2 = "최근 메일을 모두 검색한 다음에 요약해줘";
        String userId = testUser.getUserId().toString();

        //String response = mailAgent.process(userMessage, userId);
        //System.out.println("Agent Response: " + response);

        String response2 = mailAgent.process(userMessage2, userId);
        System.out.println("Agent Response: " + response2);
    }
}*/