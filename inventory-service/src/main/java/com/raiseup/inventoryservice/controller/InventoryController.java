package com.raiseup.inventoryservice.controller;

import com.raiseup.inventoryservice.dto.InventoryResponseDto;
import com.raiseup.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{sku-code}")// http://localhost:8083/api/inventory/iphone13
    @ResponseStatus(HttpStatus.OK)
    public Boolean isInStock(@PathVariable("sku-code") String skuCode){
        return inventoryService.isInStock(skuCode);
    }

    @GetMapping // http://localhost:8083/api/inventory
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponseDto>getInventories(@RequestParam("skuCodes") List<String> skuCodes){
        return inventoryService.getAllInventory(skuCodes);
    }

}
