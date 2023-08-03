package com.application.jobportal.models;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserModel {


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private long phoneNumber;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    JobApplyModel jobApplied;

}
