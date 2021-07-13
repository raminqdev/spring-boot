package com.raminq.bootifulredis;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@RedisHash("lineItems")
@Data
@AllArgsConstructor
public class LineItem implements Serializable {
    @Id
    private Long id;

    @Indexed
    private Long orderId;

    private String description;
}
