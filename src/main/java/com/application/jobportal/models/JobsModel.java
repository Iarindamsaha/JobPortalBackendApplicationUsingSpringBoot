package com.application.jobportal.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class JobsModel {

    @SequenceGenerator(name = "jobs_id_sequence", sequenceName = "jobs_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jobs_id_sequence")
    @Id
    private Integer id;
    private String companyName;
    private String techStack;
    private boolean jobOpening;

}
