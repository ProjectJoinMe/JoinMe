package com.joinme.backend.chat.repository;

import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends CrudRepository<ChatMessage, Long> {

    ChatMessage findById(long id);

    @Query("SELECT c " +
            "FROM ChatMessage c " +
            "WHERE (c.fromUser = :#{#userOne} " +
            "    AND c.toUser = :#{#userTwo}) " +
            "   OR (c.toUser = :#{#userOne} " +
            "    AND c.fromUser = :#{#userTwo})")
    List<ChatMessage> findChatMessagesWithUsers(@Param("userOne") UserAccount userOne, @Param("userTwo") UserAccount userTwo);
}
