package com.joinme.backend.notifications;

import com.joinme.backend.accounts.repository.UserAccountRepository;
import com.joinme.backend.notifications.converter.UserNotificationConverter;
import com.joinme.backend.notifications.dto.UserNotificationDto;
import com.joinme.backend.notifications.entity.UserNotification;
import com.joinme.backend.notifications.repository.UserNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
/**
 * Created by Nicole, January 2018.
 */
@Component
@Transactional
public class NotificationManagerBean {

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserNotificationConverter userNotificationConverter;

    @Autowired
    private EmailSenderBean emailSenderBean;

    /**
     * Returns notifications for user ordered descending by creation date
     * @param username
     * @return
     */
    public List<UserNotificationDto> getNotifications(String username) {
        List<UserNotification> notificationEntities = userNotificationRepository.findByUserOrderByCreationDateTimeDesc(userAccountRepository.findByUsername(username));
        return userNotificationConverter.toDto(notificationEntities);
    }

    /**
     * Creates a new UserNotification, saves it to the database and sends an email to the user
     * @param dto
     */
    public void create(UserNotificationDto dto) {
        dto.setCreationDateTime(LocalDateTime.now());
        UserNotification entity = userNotificationConverter.toEntity(dto);
        userNotificationRepository.save(entity);

        emailSenderBean.sendMailForNotificationAsync(entity);
    }

    /**
     * Marks notification as read for user with username
     * @param username
     */
    public void markNotificationsAsRead(String username) {
        List<UserNotification> notifications = userNotificationRepository.findByUserOrderByCreationDateTimeDesc(userAccountRepository.findByUsername(username));
        notifications.forEach(userNotification -> userNotification.setNotificationRead(true));
    }
}
