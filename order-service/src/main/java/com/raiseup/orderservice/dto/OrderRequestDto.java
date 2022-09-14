package com.raiseup.orderservice.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class OrderRequestDto {
    private List<OrderLineItemsDto> orderLineItemsDtoList;
}
