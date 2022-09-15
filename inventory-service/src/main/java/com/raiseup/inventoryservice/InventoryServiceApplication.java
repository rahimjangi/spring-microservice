package com.raiseup.inventoryservice;

import com.raiseup.inventoryservice.model.Inventory;
import com.raiseup.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class,args);
    }
    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository){

        return args -> {

            Inventory inv1 = Inventory.builder()
                    .skuCode("iPhone 13 Pro Max")
                    .quantity(3)
                    .build();
            Inventory inv2 = Inventory.builder()
                    .skuCode("iPhone 13 Pro")
                    .quantity(4)
                    .build();
            Inventory inv3 = Inventory.builder()
                    .quantity(6)
                    .skuCode("iPhone 13")
                    .build();
            Inventory inv4 = Inventory.builder()
                    .quantity(2)
                    .skuCode("iPhone 13 mini")
                    .build();
            Inventory inv5 = Inventory.builder()
                    .quantity(8)
                    .skuCode("iPhone 12 Pro Max")
                    .build();
            Inventory inv6 = Inventory.builder()
                    .quantity(8)
                    .skuCode("iPhone 12 Pro")
                    .build();
            Inventory inv7 = Inventory.builder()
                    .quantity(8)
                    .skuCode("iPhone 12")
                    .build();
            Inventory inv8 = Inventory.builder()
                    .quantity(8)
                    .skuCode("iPhone 12 mini")
                    .build();
            Inventory inv9 = Inventory.builder()
                    .quantity(8)
                    .skuCode("iPhone SE (2nd generation)")
                    .build();
            Inventory inv10 = Inventory.builder()
                    .quantity(8)
                    .skuCode("iPhone 11 Pro")
                    .build();
            if(inventoryRepository.count()==0){
                inventoryRepository.save(inv1);
                inventoryRepository.save(inv2);
                inventoryRepository.save(inv3);
                inventoryRepository.save(inv4);
                inventoryRepository.save(inv5);
                inventoryRepository.save(inv6);
                inventoryRepository.save(inv7);
                inventoryRepository.save(inv8);
                inventoryRepository.save(inv9);
                inventoryRepository.save(inv10);
            }
        };
    }
}
