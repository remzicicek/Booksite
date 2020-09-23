package com.Booksite.service;

import java.util.List;
import java.util.Set;


import com.Booksite.model.User;
import com.Booksite.model.UserRole;

public interface UserService {

	User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	User save(User user);
	
	List<User> listAll();
	
	User get(Long id);
	
	void delete(Long id);
	
	User findByUserName(String name);	
}
