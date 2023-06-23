package com.czmp.collections.controller;

import com.czmp.collections.dto.ChatMessageDTO;
import com.czmp.collections.dto.MessageResponseDTO;
import com.czmp.collections.model.ChatMessage;
import com.czmp.collections.model.EndUser;
import com.czmp.collections.repository.EndUserRepository;
import com.czmp.collections.repository.MessageRepository;
import com.czmp.collections.service.MessageService;
import com.czmp.collections.service.NotificationService;
import com.czmp.collections.service.exception.CannotSendMessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private EndUserRepository endUserRepository;
    @Autowired
    private NotificationService notificationService;

    @GetMapping(value ="/message/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(messageRepository.findAll());
    }

    @GetMapping(value ="/message/find/id={id}")
    public ResponseEntity<?> getById(Principal principal, @PathVariable Long id){
        Optional<EndUser> user = endUserRepository.findByUsername(principal.getName());
        if(user.isEmpty()){
            return new ResponseEntity<>(new MessageResponseDTO("Your identity could not be confirmed"), HttpStatus.UNAUTHORIZED);
        }
        try {
            Optional<ChatMessage> message = messageService.userGetsMessageById(user.get(), id);
            if(message.isPresent()){
                return ResponseEntity.ok(message.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO("Message was not found!"));
        } catch (NoPermissionException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponseDTO("You do not have permission to read this message"));
        }
    }

    @GetMapping(value = "message/find/with={id}")
    public ResponseEntity<?> getMessagesWith(Principal principal, @PathVariable Long id){
        Optional<EndUser> activeUser = endUserRepository.findByUsername(principal.getName());
        Optional<EndUser> otherUser = endUserRepository.findById(id);
        if(activeUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponseDTO("Your identity could not be confirmed"));
        }
        if(otherUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO("User with id " + id + " not found!"));
        }
        List<ChatMessage> sentMessages = messageRepository.findBySenderAndReceiverOrderBySentDate(activeUser.get(), otherUser.get());
        List<ChatMessage> receivedMessages = messageRepository.findBySenderAndReceiverOrderBySentDate(otherUser.get(), activeUser.get());
        List<ChatMessage> bothMessages = new ArrayList<>(sentMessages);
        bothMessages.addAll(receivedMessages);
        bothMessages.sort(Comparator.comparing(ChatMessage::getSentDate));
        return ResponseEntity.ok(bothMessages);
    }

    //returns list of all users you ever chated with
    @GetMapping(value = "message/get-users")
    public ResponseEntity<?> getUsers(Principal principal){
        Optional<EndUser> activeUser = endUserRepository.findByUsername(principal.getName());
        if(activeUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponseDTO("Your identity could not be confirmed"));
        }
        List<ChatMessage> sentMessages = messageRepository.findBySender(activeUser.get());
        List<ChatMessage> receivedMessages = messageRepository.findByReceiver(activeUser.get());
        Set<EndUser> usersWhoReceived = sentMessages.stream().map(ChatMessage::getReceiver).collect(Collectors.toSet());
        Set<EndUser> usersWhoSent = receivedMessages.stream().map(ChatMessage::getSender).collect(Collectors.toSet());
        usersWhoReceived.addAll(usersWhoSent);
        return ResponseEntity.ok(usersWhoReceived);
    }

    @PostMapping(value ="/message/send/receiver={id}")
    public ResponseEntity<?> sendMessage(Principal principal, @PathVariable Long id, @RequestBody ChatMessageDTO chatMessageDTO){
        Optional<EndUser> sender = endUserRepository.findByUsername(principal.getName());
        Optional<EndUser> receiver = endUserRepository.findById(id);
        if(sender.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponseDTO("Your identity could not be confirmed"));
        }
        if(receiver.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO("Receiver not found!"));
        }
        try {
            messageService.userSendsMessage(sender.get(), receiver.get(), chatMessageDTO.getMessage());
            notificationService.messageNotification(receiver.get(), sender.get(), chatMessageDTO.getMessage());
        } catch (CannotSendMessageException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponseDTO(e.getMessage()));
        }
        return ResponseEntity.ok(new MessageResponseDTO("Message sent!"));
    }

}
