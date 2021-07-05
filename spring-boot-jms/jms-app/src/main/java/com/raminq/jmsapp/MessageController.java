package com.raminq.jmsapp;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.raminq.jmsapp.JMSConfig.DESTINATION;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final JmsTemplate jmsTemplate;

    @PostMapping("/send")
    public Mono<String> send(@RequestBody Message message) {
        jmsTemplate.convertAndSend(DESTINATION, message);
        return Mono.just("Success");
    }
}
