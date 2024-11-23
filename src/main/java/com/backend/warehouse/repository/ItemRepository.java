package com.backend.warehouse.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.backend.warehouse.entity.Item;
import com.backend.warehouse.entity.Compartment;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	@Transactional
	void deleteByBookingId(Long bookingId);

	List<Item> findByNameContainingIgnoreCase(String name);

	List<Item> findByStatus(String status);

	@Query("SELECT SUM(i.quantity) FROM Item i WHERE i.status = 'Đang lưu kho'")
	Long countItemsInStock();

	@Query("SELECT MONTH(i.checkin), SUM(i.quantity) " + "FROM Item i " + "WHERE i.checkin IS NOT NULL "
			+ "GROUP BY MONTH(i.checkin) " + "ORDER BY MONTH(i.checkin)")
	List<Object[]> getMonthlyItemQuantity();
	

	@Transactional
	@Modifying
	@Query("DELETE FROM Item i WHERE i.status = :status AND i.checkout < :cutoffDate")
	int deleteItemsBeforeDate(String status, LocalDate cutoffDate);

	List<Item> findByStatusNot(String string);
}
