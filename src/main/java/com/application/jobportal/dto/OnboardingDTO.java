package com.application.jobportal.dto;

public record OnboardingDTO(String onboardingStatus,
        String candidateName,
        String candidateEmail,
        String companyName,
        String techStack) {
}
