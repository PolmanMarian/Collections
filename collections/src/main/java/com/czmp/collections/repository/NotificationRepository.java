package com.czmp.collections.repository;

import com.czmp.collections.model.EndUser;
import com.czmp.collections.model.Item;
import com.czmp.collections.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByOwnerOrderByDateDesc(EndUser owner);

    List<Notification> findByOwnerAndFromAndItemAndType(EndUser owner, EndUser from, Item item, Notification.Type type);
    List<Notification> findByOwnerAndFromAndType(EndUser owner, EndUser from, Notification.Type type);
    int countByOwnerAndDateAfter(EndUser owner, Date date);
}
