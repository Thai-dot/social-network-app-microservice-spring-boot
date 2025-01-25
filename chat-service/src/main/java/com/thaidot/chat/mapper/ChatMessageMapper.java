package com.thaidot.chat.mapper;

import com.thaidot.chat.dto.request.ChatMessageRequest;
import com.thaidot.chat.dto.response.ChatMessageResponse;
import com.thaidot.chat.entity.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {
    ChatMessage toChatMessage(ChatMessageRequest request);
    ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage);

}
