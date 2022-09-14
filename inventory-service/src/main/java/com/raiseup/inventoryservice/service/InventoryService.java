package com.raiseup.inventoryservice.service;

import com.raiseup.inventoryservice.dto.InventoryResponseDto;
import com.raiseup.inventoryservice.model.Inventory;
import com.raiseup.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public Boolean isInStock(String skuCode) {
        Optional<Inventory> optionalInventory = inventoryRepository.findBySkuCode(skuCode);
        return optionalInventory.isEmpty();
    }

    public List<InventoryResponseDto> getAllInventory(List<String> skuCodes) {
        return inventoryRepository.findBySkuCodeIn(skuCodes).stream()
                .map(this::inventoryResponseMap).toList();
    }

    private InventoryResponseDto inventoryResponseMap(Inventory inventory) {
        return InventoryResponseDto.builder()
                .skuCode(inventory.getSkuCode())
                .isInStock(inventory.getQuantity()>0)
                .build();
    }
}
