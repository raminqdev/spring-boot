package com.raminq.rabbitmq.rabbit.model;

import lombok.Data;

@Data
public class Email {
    private String to;
    private String body;
}
