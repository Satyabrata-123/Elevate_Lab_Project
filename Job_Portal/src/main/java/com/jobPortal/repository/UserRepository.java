package com.jobPortal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobPortal.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUserName(String userName); 
}
