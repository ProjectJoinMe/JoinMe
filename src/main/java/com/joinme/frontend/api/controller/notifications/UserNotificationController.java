package com.joinme.frontend.api.controller.notifications;

import com.joinme.backend.accounts.UserProfileManager;
import com.joinme.backend.accounts.dto.UserPasswordDto;
import com.joinme.backend.accounts.dto.UserProfileDto;
import com.joinme.backend.notifications.NotificationManagerBean;
import com.joinme.backend.notifications.dto.UserNotificationDto;
import com.joinme.frontend.api.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.MultipartConfigElement;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserNotificationController {

    @Autowired
    private NotificationManagerBean notificationManagerBean;

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/notifications/currentUser", method = RequestMethod.GET)
    @ResponseBody
    public List<UserNotificationDto> getNotificationsForCurrentUser() {
        String currentUsername = SecurityUtil.getCurrentUsername();
        return notificationManagerBean.getNotifications(currentUsername);
    }

}

