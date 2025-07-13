package com.jobPortal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobPortal.entity.Application;
import com.jobPortal.entity.Job;
import com.jobPortal.entity.User;
import com.jobPortal.repository.ApplicationRepository;
import com.jobPortal.service.ApplicationService;
import com.jobPortal.service.JobService;
import com.jobPortal.service.UserSerice;

@RestController
@RequestMapping("/api/Application")
public class ApplicationController {

    private final ApplicationRepository applicationRepository;

	@Autowired
	public ApplicationService applicationService;

	@Autowired
	private UserSerice userService;

	@Autowired
	private JobService jobService;

    ApplicationController(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

	@PostMapping("/applications")
	public Application applyForJob(@RequestBody Application application) {
		return applicationService.save(application);
	}
	
	@GetMapping("/applications/applicant/{username}")
	public List<Application> getApplicationsByApplicant(@PathVariable String username){
		Optional<User> applicant=userService.findByUserName(username);
		return applicant.map(applicationService::findByApplicant).orElse(List.of());
	}
	
	
	@GetMapping("/applications/job/{jobid}")
	public List<Application> getApplicationsByJob(@PathVariable Long jobId){
		Optional<Job> job=jobService.findById(jobId);
		return job.map(applicationService::findByJob).orElse(List.of());
	}
}
