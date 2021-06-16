package com.raminq.rabbitmq.rabbit.model;

import lombok.Data;

@Data
public class Sms {
    private String to;
    private String body;
}
