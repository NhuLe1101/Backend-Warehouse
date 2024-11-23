package com.backend.warehouse.service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.backend.warehouse.repository.CheckoutRecordRepository;
import com.backend.warehouse.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.LocalDate;


@Service
public class ItemCleanupService {

	    @Autowired
	    private ItemRepository itemRepository;
	    
	    @Autowired
	    private CheckoutRecordRepository checkoutrecordRepository;
	    
	    @Scheduled(cron = "0 0 0 * * ?") // Chạy mỗi ngày vào 0h sáng
	    public void deleteOldItems() {
	        // Tính thời gian 10 ngày trước
	        LocalDate cutoffDate = LocalDate.now().minusMonths(6); // 6 tháng trước

	        // Xóa các checkout records trước thời gian cutoff
	        checkoutrecordRepository.deleteCheckoutRecordsBeforeDate("Đã xuất kho", cutoffDate);

	        // Xóa các items trước thời gian cutoff
	        int deletedCount = itemRepository.deleteItemsBeforeDate("Đã xuất kho", cutoffDate);
	        
	        // In kết quả ra log
	        System.out.println("Deleted " + deletedCount + " items older than 6 months.");
	    }

	}
