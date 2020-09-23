package com.Booksite.controller;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Booksite.model.User;
import com.Booksite.model.UserRole;
import com.Booksite.service.UserService;
import com.Booksite.config.SecurityConfig;
import com.Booksite.model.Role;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String login(Model model){
		model.addAttribute("classActiveLogin", true);
		return "sign-in";
	}
	
	@RequestMapping("/createaccount")
	public String Register(Model model){
		
		User user = new User();
		
		model.addAttribute("user",user);
		
		return "Register";
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") User user) throws Exception {

		if((user.getRole()).equals("Admin")) //admin
		{
			user.setPassword(SecurityConfig.passwordEncoder(user.getPassword()));
			
			Set<UserRole> userRoles = new HashSet<>();
			Role role1= new Role();
			role1.setRoleId(0);
			role1.setName("ROLE_ADMIN");
			userRoles.add(new UserRole(user, role1));
			
			userService.createUser(user, userRoles);
		}
		else if((user.getRole()).equals("User")){
			
			user.setPassword(SecurityConfig.passwordEncoder(user.getPassword()));
			
			Set<UserRole> userRoles = new HashSet<>();
			Role role1= new Role();
			role1.setRoleId(1);
			role1.setName("ROLE_USER");
			userRoles.add(new UserRole(user, role1));
			
			userService.createUser(user, userRoles);
		}
		
		return "sign-in";
	}
}
