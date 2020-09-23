package com.Booksite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Booksite.model.Product;
import com.Booksite.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	
	public List<Product> listAll(){
		return productRepository.findAll();
	}
	
	public void save (Product product) {
		productRepository.save(product);
	}
	
	public Product getProduct(Long id) {
		return productRepository.findById(id).get();
	}
	
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
	
	
}
