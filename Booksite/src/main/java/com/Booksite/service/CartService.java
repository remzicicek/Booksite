package com.Booksite.service;

import java.util.List;

import com.Booksite.model.CartItem;
import com.Booksite.model.User;

public interface CartService {

	List<CartItem> listAll();

	CartItem save(CartItem cartItem);
	
	CartItem get(Long id);
	
	void delete(Long id);
	
	List<CartItem> getByUserId(List<CartItem> allCartItem, User user);
	
	void deleteByUserId(User user,List<CartItem> allCartItem);
	
}
