package com.application.jobportal.repository;

import com.application.jobportal.models.JobsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobsRepo extends JpaRepository<JobsModel,Integer> {

    List<JobsModel> findAllByJobOpening(boolean jobOpening);

}
