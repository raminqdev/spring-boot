package com.raminq.rabbitmq.rabbit.service;

import com.raminq.rabbitmq.rabbit.model.Email;
import com.raminq.rabbitmq.rabbit.model.Sms;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMqSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${test.rabbitmq.exchange}")
    private String exchange;
    @Value("${test.rabbitmq.email.routingKey}")
    private String emailRoutingKey;
    @Value("${test.rabbitmq.sms.routingKey}")
    private String smsRoutingKey;

    public void sendEmail(Email email) {
        rabbitTemplate.convertAndSend(exchange, emailRoutingKey, email);
    }

    public void sendSms(Sms sms) {
        rabbitTemplate.convertAndSend(exchange, smsRoutingKey, sms);
    }
}