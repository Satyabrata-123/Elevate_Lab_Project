package com.jobPortal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobPortal.entity.Application;
import com.jobPortal.entity.Job;
import com.jobPortal.entity.User;
import com.jobPortal.repository.ApplicationRepository;
import com.jobPortal.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepo;
	
	@Override
	public Application save(Application application) {
		return applicationRepo.save(application);
	}

	@Override
	public List<Application> findByApplicant(User applicant) {
		
		return applicationRepo.findByApplicant(applicant);
	}

	@Override
	public List<Application> findByJob(Job job) {
		
		return applicationRepo.findByJob(job);
	}

}
