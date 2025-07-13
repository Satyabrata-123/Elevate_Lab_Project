package com.jobPortal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobPortal.entity.Job;
import com.jobPortal.entity.User;

public interface JobRepository extends JpaRepository<Job, Long> {

	List<Job> findByTitleContaining(String keyword);
	List<Job> findByEmployer(User employer);
}
