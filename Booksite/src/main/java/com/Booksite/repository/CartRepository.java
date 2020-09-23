package com.Booksite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Booksite.model.CartItem;
import com.Booksite.model.User;

public interface CartRepository extends JpaRepository<CartItem, Long>{
	List<CartItem> findByUser(User user);
}
