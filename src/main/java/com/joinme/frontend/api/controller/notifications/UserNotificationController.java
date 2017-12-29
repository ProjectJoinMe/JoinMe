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

@Controller
public class UserNotificationController {

    @Autowired
    private NotificationManagerBean notificationManagerBean;

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/notifications", method = RequestMethod.GET)
    @ResponseBody
    public List<UserNotificationDto> getNotificationsForCurrentUser() {
        String currentUsername = SecurityUtil.getCurrentUsername();
        return notificationManagerBean.getNotifications(currentUsername);
    }

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/notifications/markAsRead", method = RequestMethod.POST)
    @ResponseBody
    public void markAsRead() {
        String currentUsername = SecurityUtil.getCurrentUsername();
        notificationManagerBean.markNotificationsAsRead(currentUsername);
    }

}

