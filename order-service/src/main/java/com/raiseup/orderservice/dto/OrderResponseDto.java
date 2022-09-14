package com.raiseup.orderservice.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private List<OrderLineItemsDto> orderLineItemsDtoList= new ArrayList<>();
}