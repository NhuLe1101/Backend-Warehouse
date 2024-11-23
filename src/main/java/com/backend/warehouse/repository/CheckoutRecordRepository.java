package com.backend.warehouse.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.warehouse.entity.CheckoutRecord;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
public interface CheckoutRecordRepository extends JpaRepository<CheckoutRecord, Long> {

    List<CheckoutRecord> findByConfirmedFalse();
    @Query("SELECT SUM(cr.quantity) FROM CheckoutRecord cr WHERE cr.item.itemId = :itemId AND cr.confirmed = true")
    int sumConfirmedQuantityByItemId(@Param("itemId") Long itemId);
    @Query("SELECT SUM(cr.quantity) FROM CheckoutRecord cr WHERE cr.item.id = :itemId AND cr.confirmed = :confirmed")
    Integer sumQuantityByItemIdAndConfirmed(@Param("itemId") Long itemId, @Param("confirmed") boolean confirmed);
    
    List<CheckoutRecord> findByCheckoutDate(LocalDate checkoutDate);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM CheckoutRecord c WHERE c.item.id IN (SELECT i.id FROM Item i WHERE i.status = :status AND i.checkout < :cutoffDate)")
    void deleteCheckoutRecordsBeforeDate(String status, LocalDate cutoffDate);
}

