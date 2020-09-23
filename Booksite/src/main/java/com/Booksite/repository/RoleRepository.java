package com.Booksite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Booksite.model.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByname(String name);
}
