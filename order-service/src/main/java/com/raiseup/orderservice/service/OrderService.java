package com.raiseup.orderservice.service;

import com.raiseup.orderservice.dto.*;
import com.raiseup.orderservice.model.Order;
import com.raiseup.orderservice.model.OrderLineItems;
import com.raiseup.orderservice.repository.OrderLineItemsRepository;
import com.raiseup.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderLineItemsRepository orderLineItemsRepository;
    private final WebClient.Builder webClient;

    public List<OrderResponseDto> getOrders() {
       return  orderRepository.findAll().stream()
                .map(this::orderMapDto)
                .toList();
    }


    public OrderResponseDto getOrder(String orderNumber) {
        Optional<Order> orderOptional = orderRepository.findOrderByOrderNumber(orderNumber);
        if(orderOptional.isEmpty()) throw  new IllegalStateException("Order does not exist");
        return orderMapDto(orderOptional.get());
    }

//    Save order
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemsList(
                        orderRequestDto.getOrderLineItemsDtoList().stream()
                                .map(this::orderLineItemsMap)
                                .toList()
                )
                .build();
//        This line tells hibernate that you have an entity to include
        order.getOrderLineItemsList().forEach(orderLineItems -> orderLineItems.setOrder(order));
        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode).toList();

        String uri = "http://inventory-service/api/inventory";
        InventoryDto[] responses = webClient.build().get()
                .uri(uri, uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryDto[].class)
                .block();

        boolean allMatch =Arrays.stream(responses)
                .allMatch(InventoryDto::getIsInStock);

        if(allMatch){

            Order savedOrder = orderRepository.save(order);
            OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                    .orderLineItemsDtoList(savedOrder.getOrderLineItemsList().stream()
                            .map(this::orderLineItemsDtoMap)
                            .toList()
                    )
                    .build();
            return orderResponseDto;
        }else throw new IllegalStateException("Product does not exist in stock");

    }


//    Helper functions
private OrderResponseDto orderMapDto(Order order) {
    return OrderResponseDto.builder()
            .orderLineItemsDtoList(order.getOrderLineItemsList().stream()
                    .map(this::orderLineItemsDtoMap).toList()
            )
            .build();
}


    private OrderLineItemsDto orderLineItemsDtoMap(OrderLineItems orderLineItems) {
        return OrderLineItemsDto.builder()
                .skuCode(orderLineItems.getSkuCode())
                .price(orderLineItems.getPrice())
                .quantity(orderLineItems.getQuantity())
                .build();
    }
    public OrderLineItems orderLineItemsMap(OrderLineItemsDto orderLineItemsDto){
        return OrderLineItems.builder()
                .skuCode(orderLineItemsDto.getSkuCode())
                .quantity(orderLineItemsDto.getQuantity())
                .price(orderLineItemsDto.getPrice())
                .build();
    }
}
