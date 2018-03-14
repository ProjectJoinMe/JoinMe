package com.joinme.frontend.api.controller.chat;

import com.joinme.backend.accounts.dto.UserProfileDto;
import com.joinme.backend.chat.ChatManager;
import com.joinme.backend.chat.dto.ChatMessageDto;
import com.joinme.frontend.api.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ChatManager chatManager;

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/chat/create", method = RequestMethod.POST)
    @ResponseBody
    public ChatMessageDto createChatMessage(@Valid @RequestBody ChatMessageDto chatMessageDto) {
        Assert.isTrue(chatMessageDto.getFromUser().getUsername().equals(SecurityUtil.getCurrentUsername()) // checks that one of the users is the current user
                || chatMessageDto.getToUser().getUsername().equals(SecurityUtil.getCurrentUsername()));

        return chatManager.createChatMessage(chatMessageDto);
    }

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/chat/getMessages", method = RequestMethod.GET)
    @ResponseBody
    public List<ChatMessageDto> getChatMessagesFromUsers(@Valid @RequestBody UserProfileDto fromUser, UserProfileDto toUser) {
        Assert.isTrue(fromUser.getUsername().equals(SecurityUtil.getCurrentUsername()) // checks that one of the users is the current user
                || toUser.getUsername().equals(SecurityUtil.getCurrentUsername()));

        return chatManager.getChatMessagesByFromUserAndToUser(fromUser, toUser);
    }
}
