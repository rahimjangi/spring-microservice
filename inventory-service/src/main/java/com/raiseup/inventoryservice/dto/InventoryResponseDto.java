package com.raiseup.inventoryservice.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Slf4j
public class InventoryResponseDto {
    private String skuCode;
    private Boolean isInStock;
}
