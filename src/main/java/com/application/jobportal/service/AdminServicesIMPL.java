package com.application.jobportal.service;

import com.application.jobportal.dto.EmailSendDTO;
import com.application.jobportal.dto.HiringStatusDTO;
import com.application.jobportal.dto.JobsDTO;
import com.application.jobportal.dto.OnboardingDTO;
import com.application.jobportal.exception.UserException;
import com.application.jobportal.models.*;
import com.application.jobportal.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminServicesIMPL implements IAdminServices{

    @Value("${rabbit.queue.name}")
    String topicExchange;
    @Value("${rabbit.queue.name}")
    String routingKey;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    JobsRepo jobsRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    JobApplyRepo jobApplyRepo;
    @Autowired
    HireRepo hireRepo;
    @Autowired
    HiringStatusDTO hiringStatusDTO;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    OnboardingRepo onboardingRepo;

    public AdminServicesIMPL(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public void addJobs(JobsDTO jobsDTO) {
        JobsModel jobs = JobsModel.builder().
                companyName(jobsDTO.companyName()).
                techStack(jobsDTO.techStack()).
                jobOpening(jobsDTO.jobOpening()).
                build();

        jobsRepo.save(jobs);

        //New Job Opening Notification
        if(jobsDTO.jobOpening()){
            List<UserModel> users = userRepo.findAll();
            for(UserModel userModel : users){
                EmailSendDTO sendDTO = new EmailSendDTO
                        ("New Job Opening Available",
                                "Greetings "+ userModel.getFirstname() + " " +userModel.getLastname()+
                                        ",\nNew Job Opening Available In Our Portal\n"+ "Company Name : "
                                        + jobsDTO.companyName() + " Tech Stack : " + jobsDTO.techStack(),
                        userModel.getEmail());
                rabbitTemplate.convertAndSend(topicExchange,routingKey,sendDTO);
            }
        }

    }

    @Override
    public void updateJobs(int id, JobsDTO updatedJobs) {

        JobsModel jobsModel = jobsRepo.findById(id).orElse(null);
        if(jobsModel == null){
            throw new UserException("Job Listing Not Available", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            jobsModel.setCompanyName(updatedJobs.companyName());
            jobsModel.setTechStack(updatedJobs.techStack());
            jobsModel.setJobOpening(updatedJobs.jobOpening());
            jobsRepo.save(jobsModel);
        }
    }

    @Override
    public JobsModel deleteJobs(int id) {

        JobsModel job = jobsRepo.findById(id).orElse(null);
        if(job == null){
            throw new UserException("No Job Listing Available", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            jobsRepo.deleteById(id);
            return job;
        }
    }

    @Override
    public List<HiringModel> hireStatusUpdate() {

        List<UserModel> users = userRepo.findAll();


        for(UserModel userModel : users){
            List<JobApplyAndJobsModel> jobApplications = jobApplyRepo.findAllByJobs(userModel.getJobApplied());
            for(JobApplyAndJobsModel jobsApplied : jobApplications){

                hiringStatusDTO.setHireStatus("applied");
                hiringStatusDTO.setEmail(userModel.getEmail());
                hiringStatusDTO.setName(userModel.getFirstname() + " " + userModel.getLastname());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS");
                String dateAndTime = jobsApplied.getApplyDateAndTime().format(formatter);
                hiringStatusDTO.setApplyDate(dateAndTime);

                hiringStatusDTO.setApplyId(userModel.getJobApplied().getApplyId());
                Optional<JobsModel> jobName = jobsRepo.findById(jobsApplied.getJobsModel().getId());
                hiringStatusDTO.setCompanyName(jobName.get().getCompanyName());
                hiringStatusDTO.setTechStack(jobName.get().getTechStack());
                HiringModel hiringModel1 = modelMapper.map(hiringStatusDTO,HiringModel.class);
                hireRepo.save(hiringModel1);
                jobApplyRepo.deleteAllByJobs(userModel.getJobApplied());

                EmailSendDTO sendDTO = new EmailSendDTO
                        ("Job Application Progress",
                                "Greetings "+ userModel.getFirstname() + " " +userModel.getLastname()+
                                        ",\nYour Job Application is being reviewed by our Hiring Team.\n"+ "Company Name : "
                                        + hiringStatusDTO.getCompanyName() + " Application Status : " + "In Progress",
                                userModel.getEmail());
                rabbitTemplate.convertAndSend(topicExchange,routingKey,sendDTO);

            }
        }

        return hireRepo.findAll();
    }

    @Override
    public OnboardingDTO onboarding(OnboardingDTO onboardingDTO) {

        OnboardingStatus onboardingEntryCheck = onboardingRepo.
                findByCandidateNameAndCompanyName(onboardingDTO.candidateName(),onboardingDTO.companyName());
        if(onboardingEntryCheck != null){
            throw  new UserException("Onboarding Details Already Available",HttpStatus.BAD_REQUEST);
        }
        else{
            OnboardingStatus onboardingStatus = OnboardingStatus.builder().
                    candidateName(onboardingDTO.candidateName()).
                    onboardingStatus(onboardingDTO.onboardingStatus()).
                    techStack(onboardingDTO.techStack()).
                    candidateEmail(onboardingDTO.candidateEmail()).
                    companyName(onboardingDTO.companyName()).build();

            onboardingRepo.save(onboardingStatus);
        }

        if (onboardingDTO.onboardingStatus().equalsIgnoreCase("selected")) {
            EmailSendDTO sendDTO = new EmailSendDTO
                    ("Job Application Status",
                            "Greetings " + onboardingDTO.candidateName() +
                                    ",\nYou Have Been Selected For OnBoarding.\n" + "Company Name : "
                                    + onboardingDTO.companyName() + " Application Status : " + "Selected" +
                                    "\nTech stack : " + onboardingDTO.techStack() +
                                    "\nPlease Provide Your Personal Details to continue onboarding.",
                            onboardingDTO.candidateEmail());
            rabbitTemplate.convertAndSend(topicExchange, routingKey, sendDTO);
        } else {
            EmailSendDTO sendDTO = new EmailSendDTO
                    ("Job Application Status",
                            "Greetings " + onboardingDTO.candidateName()+
                                    ",\nSorry you are not selected for the onboarding.\n" + "Company Name : "
                                    + onboardingDTO.companyName() + " Application Status : " + "NotSelected" +
                                    "\nTech stack : " + onboardingDTO.techStack() ,
                            onboardingDTO.candidateEmail());
            rabbitTemplate.convertAndSend(topicExchange, routingKey, sendDTO);
        }
        return onboardingDTO;
    }

    @Override
    public List<HiringModel> getHireStatus() {
        return hireRepo.findAll();
    }

}
