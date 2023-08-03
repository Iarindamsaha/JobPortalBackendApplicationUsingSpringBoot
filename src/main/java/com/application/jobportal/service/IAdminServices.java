package com.application.jobportal.service;

import com.application.jobportal.dto.HiringStatusDTO;
import com.application.jobportal.dto.JobsDTO;
import com.application.jobportal.dto.OnboardingDTO;
import com.application.jobportal.models.HiringModel;
import com.application.jobportal.models.JobsModel;

import java.util.List;

public interface IAdminServices {

    void addJobs(JobsDTO jobsDTO);
    void updateJobs (int id, JobsDTO updatedJobs);
    JobsModel deleteJobs(int id);
   List<HiringModel> hireStatusUpdate();
   OnboardingDTO onboarding(OnboardingDTO onboardingDTO);
   List<HiringModel> getHireStatus();
}
