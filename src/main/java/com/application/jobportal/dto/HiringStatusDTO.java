package com.application.jobportal.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class HiringStatusDTO {
    String name;
    String email;
    String hireStatus;
    String companyName;
    int applyId;
    String applyDate;
    String techStack;
}
