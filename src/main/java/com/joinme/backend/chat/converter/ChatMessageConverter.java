package com.joinme.backend.chat.converter;

import com.joinme.backend.accounts.converter.UserAccountConverter;
import com.joinme.backend.chat.dto.ChatMessageDto;
import com.joinme.backend.chat.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class ChatMessageConverter {

    @Autowired
    private UserAccountConverter userAccountConverter;

    public List<ChatMessageDto> toDto(List<ChatMessage> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ChatMessageDto toDto(ChatMessage entity) {
        ChatMessageDto chatMessageDto = new ChatMessageDto();
        setPropertiesOnDto(chatMessageDto, entity);
        return chatMessageDto;
    }

    private void setPropertiesOnDto(ChatMessageDto chatMessageDto, ChatMessage entity) {
        chatMessageDto.setId(entity.getId());
        chatMessageDto.setMessage(entity.getMessage());
        chatMessageDto.setFromUser(userAccountConverter.toDto(entity.getFromUser()));
        chatMessageDto.setToUser(userAccountConverter.toDto(entity.getToUser()));
        chatMessageDto.setCreationDateTime(entity.getCreationDateTime());
    }

    public ChatMessage toEntity(ChatMessageDto chatMessageDto) {
        ChatMessage chatMessageEntity = new ChatMessage();
        setPropertiesOnEntity(chatMessageEntity, chatMessageDto);
        return chatMessageEntity;
    }

    private void setPropertiesOnEntity(ChatMessage chatMessageEntity, ChatMessageDto chatMessageDto) {
        chatMessageEntity.setId(chatMessageDto.getId());
        chatMessageEntity.setMessage(chatMessageDto.getMessage());
        chatMessageEntity.setFromUser(userAccountConverter.toEntity(chatMessageDto.getFromUser()));
        chatMessageEntity.setToUser(userAccountConverter.toEntity(chatMessageDto.getToUser()));
        chatMessageEntity.setCreationDateTime(chatMessageDto.getCreationDateTime());
    }

}
