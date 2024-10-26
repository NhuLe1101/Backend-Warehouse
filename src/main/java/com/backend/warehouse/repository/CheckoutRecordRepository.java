package com.backend.warehouse.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.warehouse.entity.CheckoutRecord;

public interface CheckoutRecordRepository extends JpaRepository<CheckoutRecord, Long> {

    List<CheckoutRecord> findByConfirmedFalse();
}

