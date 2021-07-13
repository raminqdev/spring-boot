package com.raminq.bootifulredis.repository;

import com.raminq.bootifulredis.LineItem;
import org.springframework.data.repository.CrudRepository;

public interface LineItemRepository extends CrudRepository<LineItem, Long> {
}
