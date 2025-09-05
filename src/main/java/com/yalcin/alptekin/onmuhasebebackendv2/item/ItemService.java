package com.yalcin.alptekin.onmuhasebebackendv2.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemPaymentRecordRepository paymentRecordRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item addItem(Item item) {
        Item savedItem = itemRepository.save(item);
        List<ItemPaymentRecord> records = generatePaymentRecords(savedItem);
        paymentRecordRepository.saveAll(records);
        return savedItem;
    }

    private List<ItemPaymentRecord> generatePaymentRecords(Item item) {
        List<ItemPaymentRecord> records = new ArrayList<>();
        LocalDate current = item.getStartDate();
        LocalDate endDate = calculateEndDate(item);

        if ("ONCE".equals(item.getPaymentFrequencyUnit())) {
            ItemPaymentRecord record = new ItemPaymentRecord();
            record.setItem(item);
            record.setDueDate(endDate);
            record.setPaymentStatus("YAPILMADI");
            records.add(record);
            return records;
        }

            ItemPaymentRecord record = new ItemPaymentRecord();
            record.setItem(item);
            record.setDueDate(current);
            record.setPaymentStatus("YAPILMADI");
            records.add(record);

            if ("MONTH".equals(item.getPaymentFrequencyUnit())) {
                current = current.plusMonths(item.getPaymentFrequencyValue());
            } else if ("YEAR".equals(item.getPaymentFrequencyUnit())) {
                current = current.plusYears(item.getPaymentFrequencyValue());
            }
        }
        return records;
    }

    private LocalDate calculateEndDate(Item item) {
        LocalDate date = item.getStartDate();
        if ("MONTH".equals(item.getDurationUnit())) {
            date = date.plusMonths(item.getDurationValue());
        } else if ("YEAR".equals(item.getDurationUnit())) {
            date = date.plusYears(item.getDurationValue());
        }
        return date;
    }

    public List<ItemPaymentRecord> getAllPaymentRecords() {
        return paymentRecordRepository.findAll();
    }

    public List<ItemPaymentRecord> getPaymentRecordsByStatus(String status) {
        return paymentRecordRepository.findByPaymentStatus(status);
    }

    public ItemPaymentRecord updatePaymentRecord(Long id, String status, LocalDate paymentDate) {
        ItemPaymentRecord record = paymentRecordRepository.findById(id).orElseThrow();
        record.setPaymentStatus(status);
        if ("YAPILDI".equals(status)) {
            record.setPaymentDate(paymentDate);
        } else {
            record.setPaymentDate(null);
        }
        return paymentRecordRepository.save(record);
    }
}