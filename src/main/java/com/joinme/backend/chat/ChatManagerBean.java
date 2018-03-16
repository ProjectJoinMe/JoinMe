package com.joinme.backend.chat;

import com.joinme.backend.accounts.converter.UserAccountConverter;
import com.joinme.backend.accounts.dto.UserProfileDto;
import com.joinme.backend.chat.converter.ChatMessageConverter;
import com.joinme.backend.chat.dto.ChatMessageDto;
import com.joinme.backend.chat.entity.ChatMessage;
import com.joinme.backend.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class ChatManagerBean implements ChatManager {

    @Autowired
    private ChatMessageConverter chatMessageConverter;

    @Autowired
    private UserAccountConverter userAccountConverter;

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public ChatMessageDto createChatMessage(ChatMessageDto chatMessageDto) {
        ChatMessage chatMessageEntity = chatMessageConverter.toEntity(chatMessageDto);

        chatRepository.save(chatMessageEntity);

        return chatMessageConverter.toDto(chatMessageEntity);

    }

    @Override
    public List<ChatMessageDto> getChatMessagesByFromUserAndToUser(UserProfileDto fromUser, UserProfileDto toUser) {

        System.out.println(fromUser.getUsername() + toUser.getUsername());
        List<ChatMessage> chatMessageList = chatRepository.
                findChatMessagesWithUsers(
                        userAccountConverter.toEntity(fromUser), userAccountConverter.toEntity(toUser));

        System.out.println(chatMessageList.size());

        return chatMessageList.stream().map(chatMessageConverter::toDto).collect(Collectors.toList());
    }
}
