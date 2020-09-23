package com.Booksite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Booksite.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
