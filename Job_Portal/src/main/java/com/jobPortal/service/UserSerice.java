package com.jobPortal.service;

import java.util.Optional;

import com.jobPortal.entity.User;

public interface UserSerice {

	Optional<User> findByUserName(String userName);
	User save(User user); 
}
