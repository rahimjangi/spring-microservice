package com.raiseup.orderservice.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InventoryListDto {
    List<InventoryDto> inventoryDtos;
}
