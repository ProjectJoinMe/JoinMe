package com.joinme.frontend.api.controller.chat;

import com.joinme.backend.accounts.UserProfileManager;
import com.joinme.backend.accounts.dto.UserProfileDto;
import com.joinme.backend.chat.ChatManager;
import com.joinme.backend.chat.dto.ChatMessageDto;
import com.joinme.backend.notifications.NotificationManagerBean;
import com.joinme.backend.notifications.dto.UserNotificationDto;
import com.joinme.backend.notifications.dto.UserNotificationType;
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

    @Autowired
    private UserProfileManager userProfileManager;

    @Autowired
    private NotificationManagerBean notificationManagerBean;

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/chat/create", method = RequestMethod.POST)
    @ResponseBody
    public ChatMessageDto createChatMessage(@Valid @RequestBody ChatMessageDto chatMessageDto) {
        Assert.isTrue(chatMessageDto.getFromUser().getUsername().equals(SecurityUtil.getCurrentUsername())); //checks that fromUser is current user

        createNotification(UserNotificationType.chatMessageReceived, chatMessageDto,
                "Du hast eine neue Nachricht von " + chatMessageDto.getFromUser().getUsername() + " erhalten.");

        return chatManager.createChatMessage(chatMessageDto);
    }

    private void createNotification(UserNotificationType type, ChatMessageDto chatMessageDto, String message) {
        UserNotificationDto notification = new UserNotificationDto();
        notification.setType(type);
        notification.setMessage(message);
        notification.setTypeSpecificData(null);
        notification.setUsername(chatMessageDto.getToUser().getUsername());
        notificationManagerBean.create(notification);
    }


    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/chat/getMessages", method = RequestMethod.POST)
    @ResponseBody
    public List<ChatMessageDto> getChatMessagesFromUsers(@Valid @RequestBody ChatUserProfileReceiver chatUserProfileReceiver) {

        UserProfileDto fromUser = userProfileManager.getProfile(chatUserProfileReceiver.getFromUserName());
        UserProfileDto toUser = userProfileManager.getProfile(chatUserProfileReceiver.getToUserName());
        Assert.isTrue(fromUser.getUsername().equals(SecurityUtil.getCurrentUsername()) // checks that one of the users is the current user
                || toUser.getUsername().equals(SecurityUtil.getCurrentUsername()));

        return chatManager.getChatMessagesByFromUserAndToUser(fromUser, toUser);
    }


}

class ChatUserProfileReceiver {
   private String fromUserName;
   private String toUserName;

    public ChatUserProfileReceiver() {
    }

    public ChatUserProfileReceiver(String fromUserName, String toUserName) {
        this.fromUserName = fromUserName;
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }
}