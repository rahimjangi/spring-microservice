package com.raiseup.orderservice;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raiseup.orderservice.dto.OrderLineItemsDto;
import com.raiseup.orderservice.dto.OrderRequestDto;
import com.raiseup.orderservice.model.Order;
import com.raiseup.orderservice.repository.OrderRepository;
import com.raiseup.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class OrderServiceApplicationTest  {

    @Container
    private static final MySQLContainer MY_SQL_CONTAINER= new MySQLContainer("mysql:latest");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.datasource.url",MY_SQL_CONTAINER::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username",MY_SQL_CONTAINER::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password",MY_SQL_CONTAINER::getPassword);
    }
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Test
    void shouldPlaceOrder() throws Exception {
        OrderRequestDto order = makeOrderStringId();
        String orderString = objectMapper.writeValueAsString(order);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderString))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1,orderRepository.count());
    }

    @Test
    void shouldReturnOrder() throws Exception {
        OrderRequestDto order = makeOrderStringId();
        orderService.placeOrder(order);
        Assertions.assertTrue(orderRepository.count()>0);
//        mockMvc.perform(MockMvcRequestBuilders.get())
        Optional<String> optionalOrderStr = orderRepository.findAll().stream().map(Order::getOrderNumber).findFirst();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/order"+"/"+optionalOrderStr.get())).andExpect(status().isOk());

    }

    @Test
    void shouldReturnOrders() throws JsonProcessingException {
        OrderRequestDto order = makeOrderStringId();
        orderService.placeOrder(order);
        Assertions.assertTrue(orderRepository.count()>0);
    }

//    Helper method
    private OrderRequestDto makeOrderStringId() throws JsonProcessingException {
        List<OrderLineItemsDto> orderLineItemsDtoList = List.of(
                OrderLineItemsDto.builder()
                        .price(BigDecimal.valueOf(11.99))
                        .quantity(10)
                        .skuCode("123")
                        .build(),
                OrderLineItemsDto.builder()
                        .price(BigDecimal.valueOf(3.99))
                        .quantity(10)
                        .skuCode("456")
                        .build()
        );
        OrderRequestDto order = OrderRequestDto.builder()
                .orderLineItemsDtoList(orderLineItemsDtoList)
                .build();


        return order;
    }
}