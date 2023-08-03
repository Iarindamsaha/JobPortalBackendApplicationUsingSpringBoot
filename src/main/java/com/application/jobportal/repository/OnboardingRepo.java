package com.application.jobportal.repository;

import com.application.jobportal.models.OnboardingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnboardingRepo extends JpaRepository<OnboardingStatus,Integer> {
    OnboardingStatus findByCandidateNameAndCompanyName(String candidateName, String companyName);
}
