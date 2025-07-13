package com.jobPortal.service;

import java.util.List;

import com.jobPortal.entity.Application;
import com.jobPortal.entity.Job;
import com.jobPortal.entity.User;

public interface ApplicationService {

	Application save(Application application);
	List<Application> findByApplicant(User applicant);
	List<Application> findByJob(Job job);
}
