package com.Booksite.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Booksite.model.CartItem;
import com.Booksite.model.User;
import com.Booksite.repository.CartRepository;
import com.Booksite.service.CartService;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private CartRepository cartRepository;

	@Override
	public List<CartItem> listAll() {
		return cartRepository.findAll();
	}

	@Override
	public CartItem get(Long id) {
		return cartRepository.findById(id).get();
	}

	@Override
	public void delete(Long id) {
		cartRepository.deleteById(id);
	}

	@Override
	public CartItem save(CartItem cartItem) {
		return cartRepository.save(cartItem);
	}

	@Override
	public List<CartItem> getByUserId(List<CartItem> allCartItem, User user) {
		
		List<CartItem> userCartItem = new ArrayList<CartItem>();
		
		for (CartItem c : allCartItem) {
			if(c.getUser().getId()==user.getId()) {
				userCartItem.add(c);
			}
		}
		
		return userCartItem;
	}

	@Override
	public void deleteByUserId(User user, List<CartItem> allCartItem) {
		for (CartItem c : allCartItem) {
			if(c.getUser().getId() == user.getId()) {
				cartRepository.deleteById(c.getId());
			}
		}
	}
}
