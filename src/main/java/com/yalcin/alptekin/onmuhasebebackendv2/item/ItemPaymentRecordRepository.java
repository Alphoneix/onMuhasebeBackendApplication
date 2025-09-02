package com.yalcin.alptekin.onmuhasebebackendv2.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPaymentRecordRepository extends JpaRepository<ItemPaymentRecord, Long> {
    List<ItemPaymentRecord> findByPaymentStatus(String paymentStatus);
}