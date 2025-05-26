package com.carrental.services.chatService;

import java.util.List;
import java.util.Map;

public interface ChatService {

    String callChat(List<Map<String, String>> messages, String model);
}
