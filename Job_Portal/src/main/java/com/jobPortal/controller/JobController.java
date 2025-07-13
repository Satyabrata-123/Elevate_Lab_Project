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

import com.jobPortal.entity.Job;
import com.jobPortal.entity.User;
import com.jobPortal.service.JobService;
import com.jobPortal.service.UserSerice;

@RestController
@RequestMapping("/api/Job")
public class JobController {

	@Autowired
	private JobService jobService;
	
	
	@Autowired
	 private UserSerice userService;
	 
	 /*
	  * 
	  JOBS
	  */
	 
	 @PostMapping("/jobs")
	 public Job createJob(@RequestBody Job job) {
		 return jobService.save(job);
	 }
	 
	 @GetMapping("/jobs")
	 public List<Job> getAllJobs(){
		 return jobService.findAll();
	 }
	 
	 @GetMapping("/jobs/employer/{username}")
	 public List<Job> getJobsByEmployer(@PathVariable String username){
		 Optional<User> employer=userService.findByUserName(username);
		 return employer.map(jobService::findByEmployer).orElse(List.of());
	 }
	 
	 @GetMapping("/jobs/{id}")
	 public Optional<Job> getJobById(@PathVariable Long id){
		 return jobService.findById(id);
	 }
	 
}
