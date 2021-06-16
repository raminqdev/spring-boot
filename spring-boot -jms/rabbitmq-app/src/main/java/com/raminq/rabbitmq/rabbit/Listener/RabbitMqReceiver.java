package com.raminq.rabbitmq.rabbit.Listener;

import com.raminq.rabbitmq.rabbit.model.Email;
import com.raminq.rabbitmq.rabbit.model.Sms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqReceiver implements RabbitListenerConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqReceiver.class);

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
    }

    @RabbitListener(queues = "${test.rabbitmq.email.queue}")
    public void receivedEmailMessage(Email email) {
        logger.info(Thread.currentThread().getName() + "  Email Received is.. " + email);
    }

    @RabbitListener(queues = "${test.rabbitmq.sms.queue}")
    public void receivedSmsMessage(Sms sms) {
        logger.info(Thread.currentThread().getName() + "  Sms Received is.. " + sms);
    }
}