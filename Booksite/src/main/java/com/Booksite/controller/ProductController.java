package com.Booksite.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.Booksite.model.CartItem;
import com.Booksite.model.Product;
import com.Booksite.model.User;
import com.Booksite.service.CartService;
import com.Booksite.service.ProductService;
import com.Booksite.service.UserService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	/*shows products in the productlist page*/

	@RequestMapping("/")
	public String ShowProduct(Model model, Principal principal) {
		
		// User is taken to show or not show the adminportal by user role
		User user = new User();

		List<CartItem> userCartItem = new ArrayList<CartItem>();
		
		if(principal!=null) {

			// o anki kullanıcıyı verir
			user=userService.findByUserName(principal.getName());
			
			List<CartItem> allCartItem = cartService.listAll();
			
			// User is taken to show the information that will appear according to the user in the navbar in the header.html.
			userCartItem = cartService.getByUserId(allCartItem,user);
			
		}

		List<Product> allProduct = productService.listAll();
		
		model.addAttribute("listProducts",allProduct);
		
		model.addAttribute("allCartItem",userCartItem);
		
		model.addAttribute("user",user);
	
		
		return "index";
	}
	
	/*shows products in the productlist page*/
	@RequestMapping("/viewproduct")
	public String AllProduct(Model model) {
		List<Product> allProduct = productService.listAll();
		model.addAttribute("listProducts",allProduct);
		return "productlist";
	}
	
	/*creating a new product*/
	@RequestMapping("/newProduct")
	public String createNewProductForm(Model model,  HttpSession session) {
		Product product = new Product();
		session.setAttribute("mySessionAttribute", "(*) required fields");
		model.addAttribute("product",product);
		
		return "addProduct";
	}
	
	/*the product is registered in the system here*/
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product, HttpServletRequest request, HttpSession session) {
		
		session.setAttribute("mySessionAttribute", "(*) required fields");
		
		int flag=1;
		
		for(Product oneproduct : productService.listAll()) {
			/*checks for the same product*/
			if((product.getTitle().equals(oneproduct.getTitle()) && product.getAuthor().equals(oneproduct.getAuthor())
					&& product.getPublisher().equals(oneproduct.getPublisher()) && product.getIsbn().equals(oneproduct.getIsbn())) || (product.getIsbn().equals(oneproduct.getIsbn()))) {
				session.setAttribute("mySessionAttribute", "This book is existing !!");
				flag=0;
			}
		}
		
		if(flag==1) {
			MultipartFile productImage = product.getProductImage();
	
			product.setListPrice(Double.parseDouble(product.getListPriceTemporary()));
			
			product.setOurPrice(Double.parseDouble(product.getOurPriceTemporary()));
			product.setStockNumber(Integer.parseInt(product.getStockNumberTemporary()));
			
			productService.save(product);
			
			if(!productImage.isEmpty()) {
				try {
					byte[] bytes = productImage.getBytes();
					String name = product.getId() + ".jpg";
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(new File("../Booksite/src/main/resources/static/images/books/" + name)));
					stream.write(bytes);
					stream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			return "redirect:/viewproduct";
		}else {
			return "addProduct";
		}
	}
	
	/*the product is registered in the system here*/
	@RequestMapping(value="/save2", method = RequestMethod.POST)
	public String saveProduct2(@ModelAttribute("product") Product product, HttpServletRequest request, HttpSession session) {
		
		session.setAttribute("mySessionAttribute", "(*) required fields");
		
		int flag=1;
		
		for(Product oneproduct : productService.listAll()) {
			/*checks for the same product*/
			if((product.getId() != oneproduct.getId() && product.getTitle().equals(oneproduct.getTitle()) && product.getAuthor().equals(oneproduct.getAuthor())
					&& product.getPublisher().equals(oneproduct.getPublisher())&& product.getIsbn().equals(oneproduct.getIsbn())) || (product.getId() != oneproduct.getId() && product.getIsbn().equals(oneproduct.getIsbn()))) {
				session.setAttribute("mySessionAttribute", "Book information with title: "+ product.getTitle() + " cannot be updated. This book already exists.!!");
				flag=0;
			}
		}
		
		if(flag==1) {
			MultipartFile productImage = product.getProductImage();
			
			product.setListPrice(Double.parseDouble(product.getListPriceTemporary()));
			product.setOurPrice(Double.parseDouble(product.getOurPriceTemporary()));
			product.setStockNumber(Integer.parseInt(product.getStockNumberTemporary()));
	
			productService.save(product);
			
			if(!productImage.isEmpty()) {
				try {
					byte[] bytes = productImage.getBytes();
					String name = product.getId() + ".jpg";
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(new File("../Booksite/src/main/resources/static/images/books/" + name)));
					stream.write(bytes);
					stream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return "redirect:/viewproduct";
		}else {
			return "editProduct";
		}
	}
	
	
	@RequestMapping("/edit/{id}")
	public ModelAndView editProductForm(@PathVariable(name="id") Long id) {
		ModelAndView mav = new ModelAndView("editProduct");
		
		Product product = productService.getProduct(id);
		mav.addObject("product", product);
		return mav;
	}
	
	@RequestMapping(value="/delete/{id}")
	public String deleteProductForm(@PathVariable(name="id") Long id) {
	
		// If the your cardItem doesn't have a book , you can delete it. if a book exist on cartItem , the book can not delete
		productService.deleteProduct(id);
		
		return "redirect:/viewproduct";
	}
	


}
