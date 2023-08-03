package com.application.jobportal.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OnboardingStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer onboardId;

    String onboardingStatus;
    String candidateName;
    String candidateEmail;
    String companyName;
    String techStack;
}
