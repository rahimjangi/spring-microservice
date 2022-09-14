package com.raiseup.orderservice.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
@Getter
@Setter
@ToString
@Table(name = "t_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderLineItems> orderLineItemsList ;


}
