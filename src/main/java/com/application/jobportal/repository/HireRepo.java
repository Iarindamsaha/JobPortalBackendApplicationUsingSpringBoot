package com.application.jobportal.repository;

import com.application.jobportal.models.HiringModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

@Repository
public interface HireRepo extends JpaRepository<HiringModel, Integer> {

}
