package com.joinme.backend.notifications;

import com.joinme.backend.accounts.repository.UserAccountRepository;
import com.joinme.backend.notifications.converter.UserNotificationConverter;
import com.joinme.backend.notifications.dto.UserNotificationDto;
import com.joinme.backend.notifications.entity.UserNotification;
import com.joinme.backend.notifications.repository.UserNotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

@Configuration
public class MailConfiguration {

    @Bean
    public JavaMailSenderImpl mailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("projectjoinme@gmail.com");
        javaMailSender.setPassword("e91JMa%7cH3;");
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }
}
