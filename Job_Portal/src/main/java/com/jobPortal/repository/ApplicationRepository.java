package com.jobPortal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobPortal.entity.Application;
import com.jobPortal.entity.Job;
import com.jobPortal.entity.User;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

	List<Application> findByApplicant(User applicant);
	List<Application> findByJob(Job job);
}
