package com.application.jobportal.rabbit;

import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Data
public class RabbitmqConfig {

    @Value("${rabbit.queue.name}")
    String queue;
    @Value("${rabbit.queue.name}")
    String topicExchange;
    @Value("${rabbit.queue.name}")
    String routingKey;

    @Bean
    Queue queue(){
        return new Queue(queue);
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange(topicExchange);
    }

    @Bean
    Binding binding(){

        return BindingBuilder.bind(queue()).to(exchange()).with(routingKey);

    }

    @Bean
    MessageConverter messageConverter(){

        return new Jackson2JsonMessageConverter();

    }

    @Bean
    AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;

    }


}
