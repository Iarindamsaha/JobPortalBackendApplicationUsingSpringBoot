package com.application.jobportal.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class AllConfigurations {

    @Bean
    SimpleMailMessage simpleMailMessage(){
        return new SimpleMailMessage();
    }

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
