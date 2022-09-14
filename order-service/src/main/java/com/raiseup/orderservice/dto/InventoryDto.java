package com.raiseup.orderservice.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class InventoryDto {
    private String skuCode;
    private Boolean isInStock;
}
