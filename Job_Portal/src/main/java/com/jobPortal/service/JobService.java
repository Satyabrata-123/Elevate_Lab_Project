package com.jobPortal.service;

import java.util.List;
import java.util.Optional;

import com.jobPortal.entity.Job;
import com.jobPortal.entity.User;

public interface JobService {

	Job save(Job job);
	List<Job> findByEmployer(User employer);
	List<Job> findAll();
	Optional<Job> findById(Long id);
}
