package com.jobPortal.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobPortal.entity.Job;
import com.jobPortal.entity.User;
import com.jobPortal.repository.JobRepository;
import com.jobPortal.service.JobService;

@Service
public class JobServiceImpl implements JobService{

	@Autowired
	private JobRepository jobRepository;
	
	@Override
	public Job save(Job job) {
		return jobRepository.save(job);
	}

	@Override
	public List<Job> findByEmployer(User employer) {
		return jobRepository.findByEmployer(employer);
	}

	@Override
	public List<Job> findAll() {
		return jobRepository.findAll();
	}

	@Override
	public Optional<Job> findById(Long id) {
		
		return jobRepository.findById(id);
	}

}
