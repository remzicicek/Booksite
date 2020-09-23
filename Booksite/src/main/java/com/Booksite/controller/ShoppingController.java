package com.Booksite.controller;

import java.util.ArrayList;
import java.util.List;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Booksite.model.CartItem;
import com.Booksite.model.Product;
import com.Booksite.model.User;
import com.Booksite.service.CartService;
import com.Booksite.service.ProductService;
import com.Booksite.service.UserService;

@Controller
public class ShoppingController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;

	@RequestMapping("/cart")
	public String cartPage(Model model, Principal principal){
		
		// to get the current user
		User user = userService.findByUserName(principal.getName());
		
		List<CartItem> allCartItem = cartService.listAll();
		List<CartItem> userCartItem = cartService.getByUserId(allCartItem,user);
		
		model.addAttribute("userAllCartItem",userCartItem);
		model.addAttribute("user",user);
		return "cart";
	}
	
	
	
	// click on the basket icon under the book, the product id is taken and the product is found, then the cart transactions are made accordingly.
	@RequestMapping("/cart/buy/{id}")
	public String getCart(@PathVariable(name="id") Long id, Principal principal) {
		
		User user = userService.findByUserName(principal.getName());
		
		Product product = productService.getProduct(id);
		
		// takes the product, puts it in the basket and the number of products is reduced
		
		CartItem cartItem = new CartItem();
		
		List<CartItem> allCartItem = cartService.listAll();
		
		List<CartItem> userCartItem = cartService.getByUserId(allCartItem,user);
		
		// If the product is in the basket, it is only used to increase the amount and to control whether a new product will be added.
		
		boolean control = false; 

		if(userCartItem.size()!=0) {
			for (CartItem c : userCartItem) {
				if((c.getProduct().getId() == id) && control==false) {
					
					int a = c.getQuantity();
					c.setQuantity(a+1);
					cartService.save(c);
					
					int b=c.getProduct().getStockNumber();
					c.getProduct().setStockNumber(b-1);
					
					user.setCartTotal(user.getCartTotal()+product.getOurPrice());
					
					userService.save(user);
					productService.save(c.getProduct());
					control = true;
				}
			}

		}
		// a product is added
		if(control == false) {
			// cartItem'a değerler ata
			cartItem.setQuantity(1);
			cartItem.setProduct(product);
			cartItem.setUser(user);
						
			int b=product.getStockNumber();
			product.setStockNumber(b-1);
			
			user.setCartTotal(user.getCartTotal()+product.getOurPrice());
			
			userService.save(user);
			productService.save(product);
			cartService.save(cartItem);
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/deleteCart/{id}")
	public String deleteCartItem(@PathVariable(name="id") Long id, Principal principal) {
		
		User user = userService.findByUserName(principal.getName());
		
		CartItem cartItem = cartService.get(id);
		
		user.setCartTotal(user.getCartTotal()-(cartItem.getProduct().getOurPrice()*cartItem.getQuantity()));
		
		cartService.delete(id);
		
		return "redirect:/cart";
	}
	

	@RequestMapping(value="/deleteOrder")
	public String deleteOder(Principal principal) {
		
		User user = userService.findByUserName(principal.getName()); 
		
		List<CartItem> allCartItem = cartService.listAll();
		
		List<CartItem> userCartItem = cartService.getByUserId(allCartItem,user);
		
		for (CartItem c : userCartItem) {
			cartService.delete(c.getId());
		}

		return "redirect:/";
	}

	
	@RequestMapping("/order")
	public String OrderPage(Model model, Principal principal){
		
		User user = userService.findByUserName(principal.getName());
		
		
		List<CartItem> allCartItem = cartService.listAll();
		
		List<CartItem> userCartItem = new ArrayList<CartItem>();
		
		// to show user's cart in order.html.
		for (CartItem c : allCartItem) {
			if(c.getUser().getId()==user.getId()) {
				userCartItem.add(c);
			}
		}

		
		model.addAttribute("allUserCartItem",userCartItem);
		
		model.addAttribute("user",user);
		
		return "order";
	}
}
