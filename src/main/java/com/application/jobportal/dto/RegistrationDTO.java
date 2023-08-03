package com.application.jobportal.dto;

import com.application.jobportal.models.JobApplyModel;
import lombok.Data;

@Data

public class RegistrationDTO {

    String firstname;
    String lastname;
    String email;
    long phoneNumber;
    String password;
    JobApplyModel jobApplied;
}
