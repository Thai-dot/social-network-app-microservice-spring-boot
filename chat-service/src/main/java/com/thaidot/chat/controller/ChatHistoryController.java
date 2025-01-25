package com.thaidot.chat.controller;


import com.thaidot.chat.dto.ApiResponse;
import com.thaidot.chat.dto.PageResponse;
import com.thaidot.chat.entity.ChatMessage;
import com.thaidot.chat.service.ChatHistoryService;
import com.thaidot.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatHistoryController {

    private final ChatHistoryService chatHistoryService;

    @GetMapping("/history")
    public ApiResponse<PageResponse<ChatMessage>> getChatHistory(
            @RequestParam String firstUserId,
            @RequestParam String secondUserId,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return ApiResponse.<PageResponse<ChatMessage>>builder().result(chatHistoryService.getChatHistory(firstUserId, secondUserId,
                page, size)).build();
    }
}