package com.example.demo.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;




@Scope(value = "session")
@Controller(value = "userController") // Name of the bean in Spring IoC
@ELBeanName(value = "userController") // Name of the bean used by JSF
@Join(path = "/", to = "/login.jsf")
public class UserControlImpl {

	@Autowired
	UserService userService;
	private String login; 
	private String password; 
	private User user;
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	private Boolean loggedIn;
	public String doLogin() {
		String navigateTo = "null";
		User u=userService.authenticate(login, password);
		if (u != null && u.getRole() == Role.ADMINISTRATEUR) {
		navigateTo = "/admin/welcome.xhtml?faces-redirect=true";
		loggedIn = true; }
		else if (u != null && u.getRole() == Role.CLIENT){
			navigateTo = "/simulation/simulation_form.xhtml?faces-redirect=true";	
		}
		else {
		FacesMessage facesMessage =
		new FacesMessage("Login Failed: please check your username/password and try again.");
		FacesContext.getCurrentInstance().addMessage("form:btn",facesMessage);
		}
		return navigateTo;
		}
	
		public String doLogout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login.xhtml?faces-redirect=true";
		}


	
	// http://localhost:8080/SpringMVC/servlet/retrieve-all-users
	@GetMapping("/retrieve-all-users")
	@ResponseBody
	public List<User> getUsers() {
     List<User> list = userService.retrieveAllUsers();
	 return list;
	 } 

	// http://localhost:8080/SpringMVC/servlet/retrieve-user/{user-id}
	@GetMapping("/retrieve-user/{user-id}")
	@ResponseBody
	public User  retrieveUser(@PathVariable("user-id") String userId) {
		return userService.retrieveUser(userId);
	}
	
	// Ajouter User : http://localhost:8080/SpringMVC/servlet/add-user
	 @PostMapping("/add-user")
	 @ResponseBody
	 public User addUser(@RequestBody User u) {
	 User user = userService.addUser(u);
	 return user;
	 }

	// http://localhost:8080/SpringMVC/servlet/remove-user/{user-id}
	 @DeleteMapping("/remove-user/{user-id}")
	 @ResponseBody
	 public void removeUser(@PathVariable("user-id") String userId) {
      userService.deleteUser(userId);
	 }
	
	 // http://localhost:8080/SpringMVC/servlet/modify-user
	 @PutMapping("/modify-user")
	 @ResponseBody
	 public User modifyUser(@RequestBody User user) {
	 return userService.updateUser(user);
	 }

}
