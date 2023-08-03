package com.application.jobportal.repository;

import com.application.jobportal.models.JobApplyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobIdRepo extends JpaRepository<JobApplyModel,Integer> {

    JobApplyModel findByApplyId(int id);
}
