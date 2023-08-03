package com.application.jobportal.repository;

import com.application.jobportal.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserModel,Integer> {

    UserModel findByEmail(String email);
    UserModel findByPhoneNumber (long phoneNumber);
    UserModel findByEmailAndPassword(String email, String password);
}
