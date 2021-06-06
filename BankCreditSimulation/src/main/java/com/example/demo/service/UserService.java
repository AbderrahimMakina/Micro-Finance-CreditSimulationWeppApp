package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.User;

public interface UserService {
	
	public User authenticate(String login, String password) ;
	
	List<User> retrieveAllUsers();
    User addUser(User u);
	void deleteUser(String id);
	User updateUser(User u);
	 User retrieveUser(String id);

}
