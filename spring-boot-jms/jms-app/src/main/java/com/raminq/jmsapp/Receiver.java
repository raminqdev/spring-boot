package com.raminq.jmsapp;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.raminq.jmsapp.JMSConfig.DESTINATION;

@Component
public class Receiver {

    @JmsListener(destination = DESTINATION)
    public void receiveMessage(Message message) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + " ------> Message Received " + message);
    }
}
