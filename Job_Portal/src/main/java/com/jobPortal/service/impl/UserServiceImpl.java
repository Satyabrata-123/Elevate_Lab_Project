package com.jobPortal.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobPortal.entity.User;
import com.jobPortal.repository.UserRepository;
import com.jobPortal.service.UserSerice;

@Service
public class UserServiceImpl implements UserSerice{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Optional<User> findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}
	
	
}
