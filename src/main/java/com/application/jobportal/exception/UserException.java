package com.application.jobportal.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserException extends RuntimeException{

    String message;
    HttpStatus status;

}
