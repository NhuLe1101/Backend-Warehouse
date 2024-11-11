package com.backend.warehouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.warehouse.entity.Notification;
import com.backend.warehouse.entity.NotificationStatus;
import com.backend.warehouse.entity.NotificationType;
import com.backend.warehouse.scheduler.CheckoutReminderJob;
import com.backend.warehouse.service.NotificationService;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
    private CheckoutReminderJob checkoutReminderJob;
	
	@PostMapping("/create")
	public Notification createNotification(@RequestParam NotificationType type,
			@RequestParam(required = false) Long itemId, @RequestParam(required = false) Long shelfId,
			@RequestParam(required = false) Long compartmentId, @RequestParam(required = false) Long userId) {

		Notification notification = notificationService.createNotification(null, type, itemId, shelfId, compartmentId,
				userId);

		// Phát thông báo qua WebSocket nếu tạo thành công
		if (notification != null) {
			messagingTemplate.convertAndSend("/topic/notifications", notification.getMessage());
		}

		return notification;
	}
	
    @GetMapping("/checkout-reminder")
    public ResponseEntity<String> runCheckoutReminderJob() {
        checkoutReminderJob.checkItemsForCheckoutReminder();
        return ResponseEntity.ok("Checkout reminder job executed");
    }
    
	@GetMapping
	public List<Notification> getAllNotifications() {
		return notificationService.getAllNotifications();
	}

	@GetMapping("/{id}")
	public Notification getNotificationById(@PathVariable Long id) {
		return notificationService.getNotificationById(id);
	}

	@PutMapping("/{id}/mark-as-read")
    public ResponseEntity<String> markAsRead(@PathVariable Long id) {
        boolean updated = notificationService.updateNotificationStatus(id, NotificationStatus.READ);
        if (updated) {
            return ResponseEntity.ok("Notification marked as read");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
        boolean deleted = notificationService.deleteNotification(id);
        if (deleted) {
            return ResponseEntity.ok("Notification deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found");
        }
    }
}
