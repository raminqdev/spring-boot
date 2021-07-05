package com.raminq.rabbitmq.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private String port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${test.rabbitmq.exchange}")
    private String exchange;
    @Value("${test.rabbitmq.email.queue}")
    private String emailQueue;
    @Value("${test.rabbitmq.email.routingKey}")
    private String emailRoutingKey;
    @Value("${test.rabbitmq.sms.queue}")
    private String smsQueue;
    @Value("${test.rabbitmq.sms.routingKey}")
    private String smsRoutingKey;

    @Bean
    DirectExchange myDirectExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue emailQueue() {
        return new Queue(emailQueue, true);
    }

    @Bean
    Binding emailBinding() {
        return BindingBuilder.bind(emailQueue()).to(myDirectExchange()).with(emailRoutingKey);
    }

    @Bean
    Queue smsQueue() {
        return new Queue(smsQueue, true);
    }

    @Bean
    Binding smsBinding() {
        return BindingBuilder.bind(smsQueue()).to(myDirectExchange()).with(smsRoutingKey);
    }

    @Bean
    CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setPort(Integer.parseInt(port));
        return cachingConnectionFactory;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }


}
