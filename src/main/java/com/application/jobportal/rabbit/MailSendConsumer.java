package com.application.jobportal.rabbit;

import com.application.jobportal.dto.EmailSendDTO;
import com.application.jobportal.utility.JavaMailServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MailSendConsumer {

    @Autowired
    JavaMailServiceUtil javaMailServiceUtil;

    @RabbitListener(queues = "${rabbit.queue.name}" )
    public void sendEmails(EmailSendDTO emailSendDTO){
        javaMailServiceUtil.sendMail(emailSendDTO.body(),emailSendDTO.subject(),emailSendDTO.email());
        log.info("Email Sent Successfully"+"email id : " + emailSendDTO.email());
    }
}
