package com.raiseup.orderservice.controller;

import com.raiseup.orderservice.dto.OrderRequestDto;
import com.raiseup.orderservice.dto.OrderResponseDto;
import com.raiseup.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto placeOrder(@RequestBody OrderRequestDto orderRequestDto){
        log.info("OrderRequestDto: {}",orderRequestDto);
        return orderService.placeOrder(orderRequestDto);
    }

    @GetMapping("/{orderNumber}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDto getOrder(@PathVariable("orderNumber") String orderNumber){
        return orderService.getOrder(orderNumber);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponseDto>getOrders(){
        return orderService.getOrders();
    }
}
