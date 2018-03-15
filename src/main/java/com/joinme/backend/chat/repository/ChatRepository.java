package com.joinme.backend.chat.repository;

import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.chat.entity.ChatMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends CrudRepository<ChatMessage, Long> {

    ChatMessage findById(long id);

    List<ChatMessage> findTop50ByFromUserAndToUserOrderByCreationDateTimeDesc(UserAccount fromUser, UserAccount toUser);
}
