package com.czmp.collections.repository;

import com.czmp.collections.model.ChatMessage;
import com.czmp.collections.model.EndUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySenderAndReceiverOrderBySentDate(EndUser sender, EndUser receiver);
    List<ChatMessage> findBySender(EndUser sender);
    List<ChatMessage> findByReceiver(EndUser receiver);
}
