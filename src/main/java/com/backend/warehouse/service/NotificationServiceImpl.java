package com.backend.warehouse.service;

import com.backend.warehouse.entity.Notification;
import com.backend.warehouse.entity.NotificationStatus;
import com.backend.warehouse.entity.NotificationType;
import com.backend.warehouse.entity.Item;
import com.backend.warehouse.entity.Shelf;
import com.backend.warehouse.entity.User;
import com.backend.warehouse.entity.Compartment;
import com.backend.warehouse.repository.NotificationRepository;
import com.backend.warehouse.repository.ItemRepository;
import com.backend.warehouse.repository.ShelfRepository;
import com.backend.warehouse.repository.UserRepository;
import com.backend.warehouse.repository.CompartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ShelfRepository shelfRepository;

	@Autowired
	private CompartmentRepository compartmentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Override
	@Transactional
	public Notification createNotification(String message, NotificationType type, Long itemId, Long shelfId,
			Long compartmentId, Long userId) {
		Notification notification = new Notification();
		notification.setType(type);
		notification.setTimestamp(LocalDateTime.now());

		// Tìm item, shelf, compartment, và user nếu có ID
		Optional<Item> itemOpt = itemId != null ? itemRepository.findById(itemId) : Optional.empty();
		Optional<Shelf> shelfOpt = shelfId != null ? shelfRepository.findById(shelfId) : Optional.empty();
		Optional<Compartment> compartmentOpt = compartmentId != null ? compartmentRepository.findById(compartmentId)
				: Optional.empty();
		Optional<User> userOpt = userId != null ? userRepository.findById(userId) : Optional.empty();

		// Thiết lập message theo loại thông báo ITEM_CHECKOUT
		String itemName = itemOpt.map(Item::getName).orElse("Unknown Item");
		String shelfName = shelfOpt.map(Shelf::getNameShelf).orElse("Unknown Shelf");
		String compartmentName = compartmentOpt.map(Compartment::getNameComp).orElse("Unknown Compartment");
		String profileName = userOpt.map(User::getProfileName).orElse("Unknown User");

		if (type == NotificationType.ITEM_CHECKOUT) {
			message = String.format("Item %s (ID: %d) ở kệ %s, ngăn %s đã được %s checkout thành công.", itemName,
					itemId, shelfName, compartmentName, profileName);
		}

		notification.setMessage(message);

		// Thiết lập các tham chiếu
		itemOpt.ifPresent(notification::setItem);
		shelfOpt.ifPresent(notification::setShelf);
		compartmentOpt.ifPresent(notification::setCompartment);

		Notification savedNotification = notificationRepository.save(notification);

		// Phát thông báo qua WebSocket
		messagingTemplate.convertAndSend("/topic/notifications", savedNotification);

		return savedNotification;
	}

	@Override
	public List<Notification> getAllNotifications() {
		return notificationRepository.findAllByOrderByTimestampDesc();
	}

	@Override
	public Notification getNotificationById(Long id) {
		return notificationRepository.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public boolean updateNotificationStatus(Long id, NotificationStatus status) {
		Optional<Notification> notificationOpt = notificationRepository.findById(id);
		if (notificationOpt.isPresent()) {
			Notification notification = notificationOpt.get();
			notification.setStatus(status);
			notificationRepository.save(notification);
			return true;
		}
		return false;
	}
	
	@Override
	@Transactional
	public boolean deleteNotification(Long id) {
		if (notificationRepository.existsById(id)) {
			notificationRepository.deleteById(id);
			return true;
		}
		return false;
	}
}