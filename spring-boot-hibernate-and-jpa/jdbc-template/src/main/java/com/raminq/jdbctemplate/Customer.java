package com.raminq.jdbctemplate;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    @OneToMany
    @JoinColumn(name = "customer_fk")
    private Set<Order> orders = new HashSet<>();
}
