package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	
	
	@Override
	public List<User> retrieveAllUsers() {
		List<User> users=(List<User>) userRepository.findAll();
		return users;
	}
	@Override
	public User addUser(User u) {
		User userSaved=null;
		userSaved=userRepository.save(u);

		return userSaved;
	}
	@Override
	public void deleteUser(String id) {
		userRepository.deleteById(Long.parseLong(id));

	}
	@Override
	public User updateUser(User u) {


		return userRepository.save(u);
	}
	@Override
	public User retrieveUser(String id) {
		User u = userRepository.findById(Long.parseLong(id)).orElse(null);
		//User u1= userRepository.findById(Long.parseLong(id)).get();
		return u;
	}
	@Override
	public User authenticate(String login, String password) {
		return userRepository.getUserByEmailAndPassword(login, password);
	}
	
	



}
