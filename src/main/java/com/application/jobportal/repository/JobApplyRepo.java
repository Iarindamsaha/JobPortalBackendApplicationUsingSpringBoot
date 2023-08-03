package com.application.jobportal.repository;

import com.application.jobportal.models.JobApplyAndJobsModel;
import com.application.jobportal.models.JobApplyModel;
import com.application.jobportal.models.JobsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplyRepo extends JpaRepository<JobApplyAndJobsModel,Integer> {

    Optional<JobApplyAndJobsModel> findByJobsAndJobsModel (JobApplyModel jobApplyModel, Optional<JobsModel> jobsModel);
    List<JobApplyAndJobsModel> findAllByJobs(JobApplyModel jobApplyModel);

    void deleteAllByJobs(JobApplyModel jobApplyModel);


}
