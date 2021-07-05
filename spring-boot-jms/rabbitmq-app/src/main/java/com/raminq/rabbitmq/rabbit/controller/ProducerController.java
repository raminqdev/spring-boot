package com.raminq.rabbitmq.rabbit.controller;

import com.raminq.rabbitmq.rabbit.model.Email;
import com.raminq.rabbitmq.rabbit.model.Sms;
import com.raminq.rabbitmq.rabbit.service.RabbitMqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api")
public class ProducerController {

    private RabbitMqSender rabbitMqSender;

    @Autowired
    public ProducerController(RabbitMqSender rabbitMqSender) {
        this.rabbitMqSender = rabbitMqSender;
    }

    @PostMapping("/email")
    public String publishEmail() {
        Email email = new Email();
        email.setTo("test@gmail.com");
        email.setBody("Timestamp :" + LocalDateTime.now());
        rabbitMqSender.sendEmail(email);
        return "done";
    }

    @PostMapping("/sms")
    public String publishSms() {
        Sms sms = new Sms();
        sms.setTo("+912343434");
        sms.setBody("Timestamp :" + LocalDateTime.now());
        rabbitMqSender.sendSms(sms);
        return "done";
    }
}