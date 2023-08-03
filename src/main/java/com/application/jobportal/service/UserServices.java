package com.application.jobportal.service;

import com.application.jobportal.dto.LoginDTO;
import com.application.jobportal.dto.RegistrationDTO;
import com.application.jobportal.models.JobsModel;

import java.util.List;

public interface UserServices {

    void registerUser (RegistrationDTO registrationDTO);
    String login(LoginDTO loginDTO);
    List<JobsModel> getAvailableJobs();
    void applyJobs(String token, int jobID);
}
