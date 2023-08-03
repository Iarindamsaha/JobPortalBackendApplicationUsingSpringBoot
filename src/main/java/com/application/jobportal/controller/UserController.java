package com.application.jobportal.controller;

import com.application.jobportal.dto.LoginDTO;
import com.application.jobportal.dto.RegistrationDTO;
import com.application.jobportal.service.UserServices;
import com.application.jobportal.utility.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/jobPortal/user")
public class UserController {

    @Autowired
    Response response;

    @Autowired
    UserServices userServices;
    @PostMapping("/signUp")
    public ResponseEntity<Response> registerUser(@RequestBody RegistrationDTO registration){

        userServices.registerUser(registration);
        response.setMessage("User Added Successfully");
        response.setObject(registration);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginDTO loginDetails){

        String token = userServices.login(loginDetails);
        response.setMessage("User Logged In");
        response.setObject(token);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @GetMapping("/availableJobs")
    public ResponseEntity<Response> availableJobs(){

        response.setObject(userServices.getAvailableJobs());
        response.setMessage("Available Jobs Are : - ");
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @GetMapping("/apply")
    public ResponseEntity<Response> applyJob(@RequestHeader String token, @RequestParam int jobId){

        userServices.applyJobs(token,jobId);
        response.setMessage("Applied To Job Successfully");
        response.setObject(null);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }
}
