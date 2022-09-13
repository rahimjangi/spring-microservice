package com.raiseup.orderservice;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest
public class OrderServiceApplicationTest  {
    @Container
    private static final MySQLContainer MY_SQL_CONTAINER= new MySQLContainer("mysql:latest");

    @Test
    void contextLoads() {

    }
}