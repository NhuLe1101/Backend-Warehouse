package com.backend.warehouse.service;

import com.backend.warehouse.entity.Notification;
import com.backend.warehouse.entity.NotificationType;
import java.util.List;

public interface NotificationService {
    Notification createNotification(String message, NotificationType type, Long itemId, Long shelfId, Long compartmentId, Long userId);
    List<Notification> getAllNotifications();
    Notification getNotificationById(Long id);
}
