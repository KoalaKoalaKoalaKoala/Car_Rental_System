package com.carrental.services.chatService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final WebClient webClient = WebClient.create("http://localhost:11434");

    public String callChat(List<Map<String, String>> messages, String model) {
        Map<String, Object> request = Map.of(
                "model", model,
                "messages", messages,
                "stream", false
        );

        Map response = webClient.post()
                .uri("/api/chat")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Map<String, Object> message = (Map<String, Object>) response.get("message");
        return (String) message.get("content");
    }
}

