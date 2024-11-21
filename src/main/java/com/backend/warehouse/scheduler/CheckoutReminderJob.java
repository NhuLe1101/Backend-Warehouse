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

    @Scheduled(cron = "0 */30 * * * ?")
    public void checkItemsForCheckoutReminder() {
        System.out.println("Running scheduled job: checkItemsForCheckoutReminder");
        
        List<Item> items = itemRepository.findAll(); 
        System.out.println("Found " + items.size() + " items to check.");

        for (Item item : items) {
            if (item.getCheckout() != null && item.getCheckout().isEqual(LocalDate.now())) {
                System.out.println("Item " + item.getItemId() + " is due for checkout reminder today.");

                List<Compartment> compartments = compartmentRepository.findByItem_ItemId(item.getItemId());
                System.out.println("Item " + item.getItemId() + " is in " + compartments.size() + " compartments.");

                for (Compartment compartment : compartments) {
                    boolean notificationExists = notificationRepository.existsByItemAndTypeAndTimestampBetween(
                        item, NotificationType.CHECKOUT_REMINDER,
                        LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59)
                    );

                    if (!notificationExists) {
                        String shelfName = (compartment.getShelf() != null) ? compartment.getShelf().getNameShelf() : "Unknown Shelf";
                        String compartmentName = compartment.getNameComp();

                        String message = String.format("Item %s (ID: %d) ở kệ %s, ngăn %s đã đến hạn checkout.",
                                                       item.getName(), item.getItemId(), shelfName, compartmentName);

                        Notification notification = new Notification();
                        notification.setMessage(message);
                        notification.setType(NotificationType.CHECKOUT_REMINDER);
                        notification.setTimestamp(LocalDateTime.now());
                        notification.setItem(item);

                        notificationRepository.save(notification);

                        messagingTemplate.convertAndSend("/topic/notifications", notification);
                        System.out.println("Sent CHECKOUT_REMINDER for item ID: " + item.getItemId() + " in compartment ID: " + compartment.getCompId());
                    } else {
                        System.out.println("Notification already exists for item ID: " + item.getItemId() + " in compartment ID: " + compartment.getCompId());
                    }
                }
            } else {
                System.out.println("Item " + item.getItemId() + " does not meet the checkout condition.");
            }
        }
    }


}