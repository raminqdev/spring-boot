package com.raminq.bootifulredis;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@RedisHash("orders")
public class Order implements Serializable {
    @Id
    private Long id;

    @Indexed
    private LocalDate when;

    @Reference
    private List<LineItem> lineItems;
}
