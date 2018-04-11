package com.joinme.backend.chat;

import com.joinme.backend.accounts.dto.UserProfileDto;
import com.joinme.backend.chat.dto.ChatMessageDto;

import java.util.List;
/**
 * Created by Alexander, January 2018.
 */
public interface ChatManager {
    ChatMessageDto createChatMessage(ChatMessageDto chatMessageDto);

    List<ChatMessageDto> getChatMessagesByFromUserAndToUser(UserProfileDto fromUser, UserProfileDto toUser);
}
