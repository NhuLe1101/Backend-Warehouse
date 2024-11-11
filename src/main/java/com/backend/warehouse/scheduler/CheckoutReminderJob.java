package com.backend.warehouse.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.backend.warehouse.entity.Compartment;
import com.backend.warehouse.entity.Item;
import com.backend.warehouse.entity.Notification;
import com.backend.warehouse.entity.NotificationType;
import com.backend.warehouse.repository.CompartmentRepository;
import com.backend.warehouse.repository.ItemRepository;
import com.backend.warehouse.repository.NotificationRepository;
import com.backend.warehouse.repository.ShelfRepository;
import com.backend.warehouse.service.NotificationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Component
public class CheckoutReminderJob {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ItemRepository itemRepository;

	@Autowired
	private CompartmentRepository compartmentRepository;
	
    @Autowired
    private NotificationRepository notificationRepository; // Để lưu thông báo vào database

    @Scheduled(cron = "0 0 * * * ?") // Chạy mỗi giờ
    public void checkItemsForCheckoutReminder() {
        List<Item> items = itemRepository.findAll(); // Lấy tất cả các item từ cơ sở dữ liệu
        for (Item item : items) {
            if (item.getCheckout() != null && item.getCheckout().isEqual(LocalDate.now())) {
                // Lấy danh sách các compartments mà item có mặt
                List<Compartment> compartments = compartmentRepository.findByItem_ItemId(item.getItemId());

                for (Compartment compartment : compartments) {
                    // Kiểm tra xem thông báo `CHECKOUT_REMINDER` cho item và compartment đã được tạo trong ngày chưa
                    boolean notificationExists = notificationRepository.existsByItemAndTypeAndTimestampBetween(
                        item, NotificationType.CHECKOUT_REMINDER,
                        LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59)
                    );

                    if (!notificationExists) {
                        String shelfName = (compartment.getShelf() != null) ? compartment.getShelf().getNameShelf() : "Unknown Shelf";
                        String compartmentName = compartment.getNameComp();

                        // Tạo nội dung thông báo chi tiết
                        String message = String.format("Item %s (ID: %d) ở kệ %s, ngăn %s đã đến hạn checkout.",
                                                       item.getName(), item.getItemId(), shelfName, compartmentName);

                        // Tạo đối tượng thông báo và lưu vào database
                        Notification notification = new Notification();
                        notification.setMessage(message);
                        notification.setType(NotificationType.CHECKOUT_REMINDER);
                        notification.setTimestamp(LocalDateTime.now());
                        notification.setItem(item);

                        notificationRepository.save(notification); // Lưu vào database

                        // Gửi thông báo qua WebSocket
                        messagingTemplate.convertAndSend("/topic/notifications", notification);

                        // Log để kiểm tra
                        System.out.println("Sent CHECKOUT_REMINDER for item ID: " + item.getItemId() + " in compartment ID: " + compartment.getCompId());
                    }
                }
            }
        }
    }

}