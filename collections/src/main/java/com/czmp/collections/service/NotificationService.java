package com.czmp.collections.service;

import com.czmp.collections.model.Item;
import com.czmp.collections.model.Notification;
import com.czmp.collections.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.czmp.collections.model.EndUser;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    public void messageNotification(EndUser user, EndUser from, String message){
        List<Notification> notificationsToRemove = notificationRepository.findByOwnerAndFromAndType(user,from, Notification.Type.MESSAGE);
        notificationRepository.deleteAll(notificationsToRemove);

        Notification notification = new Notification();
        notification.setOwner(user);
        notification.setFrom(from);
        notification.setType(Notification.Type.MESSAGE);
        notification.setItem(null);
        notification.setDate(new Date());
        notification.setText("You got a new message from " + from.getUsername() + ".\n" + Math.min(message.length(), 50) + "...");
        notificationRepository.save(notification);
    }

    public void likeNotification(EndUser user, EndUser from, Item item){
        List<Notification> notificationsToRemove = notificationRepository.findByOwnerAndFromAndItemAndType(user,from,item, Notification.Type.LIKE);
        notificationRepository.deleteAll(notificationsToRemove);

        Notification notification = new Notification();
        notification.setOwner(user);
        notification.setFrom(from);
        notification.setType(Notification.Type.LIKE);
        notification.setItem(item);
        notification.setDate(new Date());
        notification.setText(from.getUsername() + " liked your item: " + item.getName());
        notificationRepository.save(notification);
    }

}
