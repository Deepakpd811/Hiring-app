package com.bridgelab.hiringapp.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;


@Configuration
public class RabbitMqConfig {


    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;

    @Value("${spring.rabbitmq.port}")
    private int rabbitmqPort;

    @Value("${spring.rabbitmq.username}")
    private String rabbitmqUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitmqPassword;

    @Bean
    public Queue jobOfferNotificationQueue() {
        return QueueBuilder.durable("job.offer.notification.queue").build();
    }


    @Bean
    public DirectExchange jobOfferNotificationExchange() {
        return new DirectExchange("job.offer.notification.exchange");
    }

    @Bean
    public Binding jobOfferNotificationBinding(Queue jobOfferNotificationQueue, DirectExchange jobOfferNotificationExchange) {
        return BindingBuilder.bind(jobOfferNotificationQueue)
                .to(jobOfferNotificationExchange)
                .with("job.offer.notification");
    }


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter); // Use the JSON converter.
        return rabbitTemplate;
    }


}
