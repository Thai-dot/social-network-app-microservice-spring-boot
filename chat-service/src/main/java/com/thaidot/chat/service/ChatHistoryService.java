package com.thaidot.chat.service;

import com.thaidot.chat.dto.PageResponse;
import com.thaidot.chat.entity.ChatMessage;
import com.thaidot.chat.entity.ChatRoom;
import com.thaidot.chat.exception.AppException;
import com.thaidot.chat.exception.ErrorCode;
import com.thaidot.chat.mapper.ChatMessageMapper;
import com.thaidot.chat.repository.ChatMessageRepository;
import com.thaidot.chat.utils.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatHistoryService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final ChatRoomService chatRoomService;
    private final EncryptionUtil encryptionUtil;

    public PageResponse<ChatMessage> getChatHistory(String firstUserId, String secondUserId, int page, int size) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = (Jwt) principal;
        String userID = jwt.getSubject().toString();
        if (!firstUserId.equals(userID) && !secondUserId.equals(userID)) {
            throw new AppException(ErrorCode.NOT_ALLOWED);
        }

        Sort sort = Sort.by(Sort.Order.desc("timestamp"));
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        ChatRoom room = chatRoomService.findRoom(firstUserId, secondUserId).orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        Page<ChatMessage> pageData = chatMessageRepository.findByRoomId(room.getId(), pageable);

        List<ChatMessage> decryptMessages = pageData.getContent().stream()
                .map(chatMessage -> {
                    chatMessage.setContent(encryptionUtil.decrypt(chatMessage.getContent()));
                    return chatMessage;
                })
                .toList();

        return PageResponse.<ChatMessage>builder()
                .currentPage(page)
                .totalPages(pageData.getTotalPages())
                .pageSize(pageData.getSize())
                .totalElements(pageData.getTotalElements())
                .data(decryptMessages)
                .build();
    }
}
