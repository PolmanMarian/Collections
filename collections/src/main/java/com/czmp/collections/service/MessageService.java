package com.czmp.collections.service;

import com.czmp.collections.model.ChatMessage;
import com.czmp.collections.model.EndUser;
import com.czmp.collections.repository.MessageRepository;
import com.czmp.collections.service.exception.CannotSendMessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.nio.channels.NoConnectionPendingException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Optional<ChatMessage> userGetsMessageById(EndUser user, Long id) throws NoPermissionException {
        Optional<ChatMessage> message = messageRepository.findById(id);
        if(message.isPresent()){
            if(!message.get().getSender().getId().equals(user.getId()) &&
                    !message.get().getReceiver().getId().equals( user.getId())){
                throw new NoPermissionException();
            }
        }
        return message;
    }

    public void userSendsMessage(EndUser sender, EndUser receiver, String text) throws CannotSendMessageException {
        if(sender.getId().equals(receiver.getId())){
            throw new CannotSendMessageException("Sender and receiver can not be the same user");
        }
        ChatMessage message = new ChatMessage();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setSentDate(new Date());
        message.setMessage(text);

        messageRepository.save(message);
    }

}
