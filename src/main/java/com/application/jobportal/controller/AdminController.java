package com.application.jobportal.controller;

import com.application.jobportal.dto.JobsDTO;
import com.application.jobportal.dto.OnboardingDTO;
import com.application.jobportal.models.JobsModel;
import com.application.jobportal.service.IAdminServices;
import com.application.jobportal.utility.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    @Autowired
    Response response;
    @Autowired
    IAdminServices adminServices;

    @PostMapping("/addJobs")
    public ResponseEntity<Response> addJobs(@RequestBody JobsDTO jobs){

        adminServices.addJobs(jobs);
        response.setMessage("Jobs Added Successfully");
        response.setObject(jobs);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping("/updateJobs/{id}")
    public ResponseEntity<Response> updateBooks(@PathVariable int id , @RequestBody JobsDTO updatedJobs){

        adminServices.updateJobs(id,updatedJobs);
        response.setMessage("Jobs Updated Successfully");
        response.setObject(updatedJobs);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @DeleteMapping("/deleteJobs")
    public ResponseEntity<Response> deleteBook (@RequestParam int id){

        JobsModel deletedJob = adminServices.deleteJobs(id);
        response.setMessage("Job Listing Deleted Successfully");
        response.setObject(deletedJob);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @GetMapping("/hireStatusUpdate")
    public ResponseEntity<Response> hiringStatusUpdate(){


        response.setMessage("Status : -");
        response.setObject(adminServices.hireStatusUpdate());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/onboarding")
    public ResponseEntity<Response> onboarding (@RequestBody OnboardingDTO onboardingDTO){

        adminServices.onboarding(onboardingDTO);
        response.setMessage("Onboarding Details updated SuccessFully");
        response.setObject(onboardingDTO);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @GetMapping("/hireStatus")
    public ResponseEntity<Response> getHireDetails(){

        response.setMessage("Hire Status Are : -");
        response.setObject(adminServices.getHireStatus());
        return new ResponseEntity<>(response,HttpStatus.OK);

    }



}
