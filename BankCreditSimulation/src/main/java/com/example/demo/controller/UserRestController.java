package com.example.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	UserService userservice;
	
	
	@GetMapping("/retrieve_all_Users")
	public List<User> retrieveAllusers() {
		List<User> list=userservice.retrieveAllUsers();

		return list;
	}
	
	
	@GetMapping("/retrieve_User/{id}")
	public User retrieveuser(@PathVariable("id") String id){
		return userservice.retrieveUser(id);
	
	}
	
	
	@PostMapping("/add_User")

	public User adduser (@RequestBody User u){
		User users=userservice.addUser(u);
		return users;

	}

	
	@DeleteMapping("/remove_User/{id}")

	public  void removeuser(@PathVariable("id") String id){
		userservice.deleteUser(id);


	}
	
	
	@PutMapping("modify_user")
	public User updateuser (@RequestBody User u){
		return userservice.updateUser(u);


	}



}
