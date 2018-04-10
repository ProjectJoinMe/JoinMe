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

/**
 * Created by Alexander January 2018.
 */
@Controller
public class ChatController {

    @Autowired
    private ChatManager chatManager;

    @Autowired
    private UserProfileManager userProfileManager;

    @Autowired
    private NotificationManagerBean notificationManagerBean;

    /**
     * Receives the request to create a new chat message and creates the notification for the user receiving.
     * Forwards request to the ChatManager
     *
     * @param chatMessageDto The newly sent chat message received in the request
     * @return ChatMessageDto of the created message
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/chat/create", method = RequestMethod.POST)
    @ResponseBody
    public ChatMessageDto createChatMessage(@Valid @RequestBody ChatMessageDto chatMessageDto) {
        Assert.isTrue(chatMessageDto.getFromUser().getUsername().equals(SecurityUtil.getCurrentUsername())); //checks that fromUser is current user

        createNotification(UserNotificationType.chatMessageReceived, chatMessageDto, //created chatmessage
                "Du hast eine neue Nachricht von " + chatMessageDto.getFromUser().getUsername() + " erhalten.");

        return chatManager.createChatMessage(chatMessageDto);
    }

    /**
     * Method to help create the notification for the user receiving a chat message.
     * uses NotificationManager for actual creation
     *
     * @param type           of UserNotification
     * @param chatMessageDto the notification is for
     * @param message        in form of a String to be sent
     */
    private void createNotification(UserNotificationType type, ChatMessageDto chatMessageDto, String message) {
        UserNotificationDto notification = new UserNotificationDto();
        notification.setType(type);
        notification.setMessage(message);
        notification.setTypeSpecificData(null);
        notification.setUsername(chatMessageDto.getToUser().getUsername());
        notificationManagerBean.create(notification);
    }

    /**
     * Returns the messages exchanged by two users. Data gotten from the ChatManager
     * First gets the UserProfileDtos from the users in question from the UserProfileManager
     *
     * @param chatUserNameReceiver the usernames of the users to look for
     * @return the chat messages between the users
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/chat/getMessages", method = RequestMethod.POST)
    @ResponseBody
    public List<ChatMessageDto> getChatMessagesFromUsers(@Valid @RequestBody ChatUserNameReceiver chatUserNameReceiver) {

        UserProfileDto fromUser = userProfileManager.getProfile(chatUserNameReceiver.getFromUserName());
        UserProfileDto toUser = userProfileManager.getProfile(chatUserNameReceiver.getToUserName());
        Assert.isTrue(fromUser.getUsername().equals(SecurityUtil.getCurrentUsername())
                || toUser.getUsername().equals(SecurityUtil.getCurrentUsername()));// checks that one of the users is the current user

        List<ChatMessageDto> chatMessages = chatManager.getChatMessagesByFromUserAndToUser(fromUser, toUser);
        return chatMessages;
    }


}

/**
 * Created by Alexander January 2018.
 * <p>
 * This class is used to receive the usernames for getChatMessagesFromUsers within one request body. See above.
 */
class ChatUserNameReceiver {
    private String fromUserName;
    private String toUserName;

    public ChatUserNameReceiver() {
    }

    public ChatUserNameReceiver(String fromUserName, String toUserName) {
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