package com.backend.warehouse.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.backend.warehouse.entity.Compartment;
import com.backend.warehouse.entity.Item;
import com.backend.warehouse.entity.NotificationType;
import com.backend.warehouse.repository.ItemRepository;
import com.backend.warehouse.service.NotificationService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class CheckoutReminderJob {

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private ItemRepository itemRepository;

	@Scheduled(cron = "0 0 * * * ?") // Chạy mỗi giờ
	public void checkItemsForCheckoutReminder() {
	    List<Item> items = itemRepository.findAll();
	    for (Item item : items) {
	        if (item.getCheckout() != null && (item.getCheckout().isBefore(LocalDate.now()) || item.getCheckout().isEqual(LocalDate.now()))) {
	            List<Compartment> compartments = item.getCompartments(); // Lấy danh sách compartments từ item
	            
	            if (compartments != null && !compartments.isEmpty()) {
	                for (Compartment compartment : compartments) {
	                    Long shelfId = (compartment.getShelf() != null) ? compartment.getShelf().getShelfId() : null;
	                    Long compartmentId = compartment.getCompId();
	                    
	                    notificationService.createNotification(
	                        null,
	                        NotificationType.CHECKOUT_REMINDER,
	                        item.getItemId(),
	                        shelfId,
	                        compartmentId,
	                        null // Không cần userId cho loại này
	                    );
	                }
	            }
	        }
	    }
	}


}
