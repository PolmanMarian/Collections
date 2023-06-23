package com.czmp.collections.controller;

import com.czmp.collections.dto.CountDTO;
import com.czmp.collections.dto.MessageResponseDTO;
import com.czmp.collections.repository.EndUserRepository;
import com.czmp.collections.repository.NotificationRepository;
import com.czmp.collections.model.Notification;
import com.czmp.collections.model.EndUser;
import com.czmp.collections.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class NotificationController {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    NotificationService notificationService;
    @Autowired
    EndUserRepository endUserRepository;

    @GetMapping(value = "/notification/all")
    ResponseEntity<?> getAll(Principal principal){
        Optional<EndUser> user = endUserRepository.findByUsername(principal.getName());
        if(user.isEmpty()) {
            return new ResponseEntity<>(new MessageResponseDTO("Your identity could not be confirmed"), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(notificationRepository.findAll());
    }

    @GetMapping(value = "/notification/find/limit={limit}")
    ResponseEntity<?> getNotifications(Principal principal, @PathVariable Integer limit){
        Optional<EndUser> user = endUserRepository.findByUsername(principal.getName());
        if(user.isEmpty()) {
            return new ResponseEntity<>(new MessageResponseDTO("Your identity could not be confirmed"), HttpStatus.UNAUTHORIZED);
        }
        List<Notification> notifications = notificationRepository.findByOwnerOrderByDateDesc(user.get());
        notifications = notifications.stream().limit(limit).toList();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping(value = "/notification/count")
    ResponseEntity<?> getCount(Principal principal){
        Optional<EndUser> user = endUserRepository.findByUsername(principal.getName());
        if(user.isEmpty()) {
            return new ResponseEntity<>(new MessageResponseDTO("Your identity could not be confirmed"), HttpStatus.UNAUTHORIZED);
        }
        int count = notificationRepository.countByOwnerAndDateAfter(user.get(), user.get().getLastSeenNotification());
        return ResponseEntity.ok(new CountDTO(count));
    }

    @GetMapping(value = "notification/seen")
    ResponseEntity<?> seenNotifications(Principal principal){
        Optional<EndUser> user = endUserRepository.findByUsername(principal.getName());
        if(user.isEmpty()) {
            return new ResponseEntity<>(new MessageResponseDTO("Your identity could not be confirmed"), HttpStatus.UNAUTHORIZED);
        }
        user.get().setLastSeenNotification(new Date());
        endUserRepository.save(user.get());
        return ResponseEntity.ok(new MessageResponseDTO("Successfully seen notifications!"));
    }
}