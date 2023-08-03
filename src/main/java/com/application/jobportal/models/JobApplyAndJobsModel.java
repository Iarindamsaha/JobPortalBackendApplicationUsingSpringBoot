package com.application.jobportal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplyAndJobsModel {

    @SequenceGenerator(name = "job_sequence", sequenceName = "job_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_sequence")
    @Id
    private Integer id;
    @ManyToOne
    JobApplyModel jobs;
    @ManyToOne
    JobsModel jobsModel;
    Boolean applyStatus;
    String application;
    LocalDateTime applyDateAndTime;






}
