package com.application.jobportal.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LoginDTO {

    String email;
    String password;
}
