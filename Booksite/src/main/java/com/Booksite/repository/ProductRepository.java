package com.Booksite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Booksite.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
