package com.raminq.bootifulredis.repository;

import com.raminq.bootifulredis.Order;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Collection;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Collection<Order> findByWhen(LocalDate when);
}
