package com.application.jobportal.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class JobApplyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int applyId;
}
