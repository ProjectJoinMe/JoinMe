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
/**
 * Created by Alexander, January 2018.
 */
@Component
@Transactional
public class ChatManagerBean implements ChatManager {

    @Autowired
    private ChatMessageConverter chatMessageConverter;

    @Autowired
    private UserAccountConverter userAccountConverter;

    @Autowired
    private ChatRepository chatRepository;

    /**
     * Creates ChatMessageEntity from dto
     * @param chatMessageDto
     * @return
     */
    @Override
    public ChatMessageDto createChatMessage(ChatMessageDto chatMessageDto) {
        ChatMessage chatMessageEntity = chatMessageConverter.toEntity(chatMessageDto);

        chatRepository.save(chatMessageEntity);

        return chatMessageConverter.toDto(chatMessageEntity);

    }

    /**
     * Returns ChatMessages for two users
     * @param fromUser sender
     * @param toUser message reciepient
     * @return
     */
    @Override
    public List<ChatMessageDto> getChatMessagesByFromUserAndToUser(UserProfileDto fromUser, UserProfileDto toUser) {

        List<ChatMessage> chatMessageList = chatRepository.
                findChatMessagesWithUsers(
                        userAccountConverter.toEntity(fromUser), userAccountConverter.toEntity(toUser));


        return chatMessageList.stream().map(chatMessageConverter::toDto).collect(Collectors.toList());
    }
}
