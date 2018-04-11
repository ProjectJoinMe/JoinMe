package com.joinme.frontend.api.controller.notifications;

import com.joinme.backend.notifications.NotificationManagerBean;
import com.joinme.backend.notifications.dto.UserNotificationDto;
import com.joinme.frontend.api.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Nicole August 2017.
 */
@Controller
public class UserNotificationController {


    @Autowired
    private NotificationManagerBean notificationManagerBean;

    /**
     * Returns the notifications for the current user. Gets data from the NotificationsManager
     *
     * @return List of UserNotificationDto with all new notifications.
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/notifications", method = RequestMethod.GET)
    @ResponseBody
    public List<UserNotificationDto> getNotificationsForCurrentUser() {
        String currentUsername = SecurityUtil.getCurrentUsername();
        return notificationManagerBean.getNotifications(currentUsername);
    }

    /**
     * Receives the request to mark a notification as read.
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/notifications/markAsRead", method = RequestMethod.POST)
    @ResponseBody
    public void markAsRead() {
        String currentUsername = SecurityUtil.getCurrentUsername();
        notificationManagerBean.markNotificationsAsRead(currentUsername);
    }

}

