package com.Booksite.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Booksite.repository.RoleRepository;
import com.Booksite.repository.UserRepository;
import com.Booksite.service.UserService;
import com.Booksite.model.User;
import com.Booksite.model.UserRole;


@Service
public class UserServiceImpl implements UserService{

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> listAll() {
		return userRepository.findAll();
	}

	@Override
	public User get(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findByUserName(String name) {
		for(User oneuser :userRepository.findAll()) {
			
			if(oneuser.getUsername().equals(name)){
								
				return oneuser;
			}	
			
		}
		return null;
	}

	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {
		User localUser = userRepository.findByUsername(user.getUsername());

		if (localUser != null) {
			LOG.info("user {} already exists.", user.getUsername());
		} else {
			for (UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());
			}

			user.getUserRoles().addAll(userRoles);
			
			

			localUser = userRepository.save(user);
		}

		return localUser;
	}

}
