package com.backend.warehouse.repository;

import com.backend.warehouse.entity.Item;
import com.backend.warehouse.entity.Notification;
import com.backend.warehouse.entity.NotificationType;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByOrderByTimestampDesc();
    boolean existsByItemAndTypeAndTimestampBetween(Item item, NotificationType type, LocalDateTime start, LocalDateTime end);

}

