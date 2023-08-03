package com.application.jobportal.service;

import com.application.jobportal.dto.EmailSendDTO;
import com.application.jobportal.dto.LoginDTO;
import com.application.jobportal.dto.RegistrationDTO;
import com.application.jobportal.exception.UserException;
import com.application.jobportal.models.JobApplyAndJobsModel;
import com.application.jobportal.models.JobApplyModel;
import com.application.jobportal.models.JobsModel;
import com.application.jobportal.models.UserModel;
import com.application.jobportal.repository.JobApplyRepo;
import com.application.jobportal.repository.JobIdRepo;
import com.application.jobportal.repository.JobsRepo;
import com.application.jobportal.repository.UserRepo;
import com.application.jobportal.utility.JwtUtility;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServicesIMPL implements UserServices {

    @Value("${rabbit.queue.name}")
    String queue;
    @Value("${rabbit.queue.name}")
    String topicExchange;
    @Value("${rabbit.queue.name}")
    String routingKey;


    @Autowired
    JwtUtility jwtUtility;
    @Autowired
    UserRepo userRepo;
    @Autowired
    JobIdRepo applyIdRepo;
    @Autowired
    JobsRepo jobsRepo;
    @Autowired
    JobApplyRepo jobApplyRepo;
    @Autowired
    ModelMapper modelMapper;
    private final RabbitTemplate rabbitTemplate;

    public UserServicesIMPL(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void registerUser(RegistrationDTO registrationDTO) {

        UserModel emailCheck = userRepo.findByEmail(registrationDTO.getEmail());
        if(emailCheck != null){
            throw new UserException
                    ("Email Already Exits || Please use A Different Email", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserModel phoneNumberCheck = userRepo.findByPhoneNumber(registrationDTO.getPhoneNumber());
        if(phoneNumberCheck != null){
            throw new UserException
                    ("Phone Number Already Taken || Use Different Phone Number", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserModel registerUser = modelMapper.map(registrationDTO,UserModel.class);
        userRepo.save(registerUser);

        EmailSendDTO sendDTO = new EmailSendDTO
                ("Welcome To Our JOB PORTAL",
                        "Greetings "+ registerUser.getFirstname() + " " +registerUser.getLastname()+
                        "Welcome to Our Website Your One Stop Destination to find perfect Jobs",
                        registerUser.getEmail());

        rabbitTemplate.convertAndSend(topicExchange,routingKey,sendDTO);

    }

    @Override
    public String login(LoginDTO loginDTO) {

        UserModel getUser = userRepo.findByEmailAndPassword(loginDTO.getEmail(),loginDTO.getPassword());
        if(getUser == null){
            throw new UserException("User Not Registered",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            return jwtUtility.generateToken(loginDTO);
        }
    }

    @Override
    public List<JobsModel> getAvailableJobs() {

        return jobsRepo.findAllByJobOpening(true);

    }


    @Override
    public void applyJobs(String token, int jobID) {

        LoginDTO loginDTO = jwtUtility.decodeToken(token);
        if(loginDTO == null){
            throw new UserException("Something Went Wrong",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserModel userModel = userRepo.findByEmailAndPassword(loginDTO.getEmail(),loginDTO.getPassword());
        JobApplyModel jobSelected = applyIdRepo.findByApplyId(userModel.getJobApplied().getApplyId());
        Optional<JobsModel> job = jobsRepo.findById(jobID);
        Optional<JobApplyAndJobsModel> jobApplyAndJobsModel = jobApplyRepo.
                findByJobsAndJobsModel(userModel.getJobApplied(),job);

        if(job.isPresent()){
            if(jobApplyAndJobsModel.isPresent()){
                throw new UserException("Job Already Applied",HttpStatus.INTERNAL_SERVER_ERROR);
            }
            else {
                JobApplyAndJobsModel applyAndJobsModel = new JobApplyAndJobsModel();
                applyAndJobsModel.setJobs(jobSelected);
                applyAndJobsModel.setJobsModel(job.get());
                applyAndJobsModel.setApplyStatus(true);
                applyAndJobsModel.setApplication("In Progress");
                applyAndJobsModel.setApplyDateAndTime(LocalDateTime.now());
                jobApplyRepo.save(applyAndJobsModel);

            }
        }
        else {
            throw new UserException("Job Not Available",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
