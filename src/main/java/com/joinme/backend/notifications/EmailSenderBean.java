package com.joinme.backend.notifications;

import com.joinme.backend.notifications.entity.UserNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EmailSenderBean {

    @Autowired
    private MailSender mailSender;

    private SimpleMailMessage templateMessage;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    public void setupMailTemplate() {
        templateMessage = new SimpleMailMessage();
        templateMessage.setFrom("projectjoinme@gmail.com");
    }

    @Async
    public void sendMailForNotificationAsync(UserNotification notification) {
        String email = notification.getUser().getEmail();
        if (email != null) {
            logger.info("Sending mail for notification to " + email);
            SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
            msg.setTo(email);
            msg.setSubject("JoinMe - Neue Benachrichtigung!");
            msg.setText(notification.getMessage());
            try {
                this.mailSender.send(msg);
            } catch (MailException ex) {
                logger.warn("Could not send notification with id " + notification.getId() + " as mail, target mail was " + email, ex);
            }
        }
    }
}
