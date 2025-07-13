package com.jobPortal.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobPortal.entity.User;
import com.jobPortal.service.UserSerice;

@RestController
@RequestMapping("/api/User")
public class UserController {

	 @Autowired
	 private UserSerice userService;
	 
	 
	 
	 /*
	  * 
	  USERS
	  */
	 @PostMapping("/users")
	 public User createUser(@RequestBody User user) {
		 return userService.save(user);
	 }
	 
	 @GetMapping("/users/{userName}")
	 public Optional<User> getUserByUserName(@PathVariable String userName){
		 return userService.findByUserName(userName);
	 }
	 
	 
}
