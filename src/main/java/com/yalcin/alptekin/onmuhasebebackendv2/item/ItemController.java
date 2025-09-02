package com.yalcin.alptekin.onmuhasebebackendv2.item;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> getItems() {
        return itemService.getAllItems();
    }

    @PostMapping
    public Item addItem(@RequestBody Item item) {
        return itemService.addItem(item);
    }

    @GetMapping("/payment-records")
    public List<ItemPaymentRecord> getAllPaymentRecords() {
        return itemService.getAllPaymentRecords();
    }

    @GetMapping("/payment-records/status/{status}")
    public List<ItemPaymentRecord> getPaymentRecordsByStatus(@PathVariable String status) {
        return itemService.getPaymentRecordsByStatus(status);
    }

    @PutMapping("/payment-records/{id}")
    public ItemPaymentRecord updatePaymentRecord(@PathVariable Long id, @RequestBody UpdatePaymentRecordRequest request) {
        return itemService.updatePaymentRecord(id, request.getStatus(), request.getPaymentDate());
    }
}

class UpdatePaymentRecordRequest {
    private String status;
    private LocalDate paymentDate;

    public String getStatus() {
        return status;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }
}