package com.raiseup.inventoryservice.service;

import com.raiseup.inventoryservice.model.Inventory;
import com.raiseup.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public Boolean isInStock(String skuCode) {
        Optional<Inventory> optionalInventory = inventoryRepository.findBySkuCode(skuCode);
        return optionalInventory.isEmpty();
    }
}
