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

        int numberOfPayments = 1;

        if ("ONCE".equals(item.getPaymentFrequencyUnit())) {
            numberOfPayments = 1;
        } else if ("MONTH".equals(item.getPaymentFrequencyUnit())) {
            if ("YEAR".equals(item.getDurationUnit())) {
                numberOfPayments = item.getDurationValue() * 12;
            } else {
                numberOfPayments = item.getDurationValue();
            }
        } else if ("YEAR".equals(item.getPaymentFrequencyUnit())) {
            numberOfPayments = item.getDurationValue();
        }

        double paymentAmount = (item.getTotalAmount() != null && numberOfPayments > 0)
                ? item.getTotalAmount() / numberOfPayments
                : 0.0;

        if ("ONCE".equals(item.getPaymentFrequencyUnit())) {
            ItemPaymentRecord record = new ItemPaymentRecord();
            record.setItem(item);
            record.setDueDate(endDate);
            record.setPaymentStatus("YAPILMADI");
            record.setAmount(item.getTotalAmount());
            records.add(record);
            return records;
        }

        int paymentIndex = 0;
        while (!current.isAfter(endDate) && paymentIndex < numberOfPayments) {
            ItemPaymentRecord record = new ItemPaymentRecord();
            record.setItem(item);
            record.setDueDate(current);
            record.setPaymentStatus("YAPILMADI");
            record.setAmount(paymentAmount);
            records.add(record);

            paymentIndex++;
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