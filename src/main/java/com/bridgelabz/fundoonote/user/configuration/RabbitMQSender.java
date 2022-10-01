package com.bridgelabz.fundoonote.user.configuration;

import com.bridgelabz.fundoonote.user.util.MailObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("EXCHANGE")
    private String exchange;
    @Value("ROUTING_KEY")
    private String routingKey;

    public void send(MailObject message) {
        rabbitTemplate.convertAndSend(exchange,routingKey,message);
    }
}
