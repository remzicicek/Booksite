package com.Booksite.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String title;
	
	@NotBlank
	private String author;
	private String publisher;
	private String publicationDate;
	private String category;
	private String format;
	private double listPrice;
	private double ourPrice;
	private int pageNumber;
	private int stockNumber;
	
	@NotBlank
	private String isbn;
	
	@Column(columnDefinition="text")
	private String description;
	
	@Transient
	private MultipartFile productImage;
	
	private String listPriceTemporary;

	private String ourPriceTemporary;

	private String stockNumberTemporary;
	
	public Product() {
		
	}


	


	public Product(Long id, @NotBlank String title, @NotBlank String author, String publisher, String publicationDate,
			String category, String format, double listPrice, double ourPrice, int pageNumber, int stockNumber,
			@NotBlank String isbn, String description, MultipartFile productImage, String listPriceTemporary,
			String ourPriceTemporary, String stockNumberTemporary) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.category = category;
		this.format = format;
		this.listPrice = listPrice;
		this.ourPrice = ourPrice;
		this.pageNumber = pageNumber;
		this.stockNumber = stockNumber;
		this.isbn = isbn;
		this.description = description;
		this.productImage = productImage;
		this.listPriceTemporary = listPriceTemporary;
		this.ourPriceTemporary = ourPriceTemporary;
		this.stockNumberTemporary = stockNumberTemporary;
	}





	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getPublisher() {
		return publisher;
	}


	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}


	public String getPublicationDate() {
		return publicationDate;
	}


	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getFormat() {
		return format;
	}


	public void setFormat(String format) {
		this.format = format;
	}


	public double getListPrice() {
		return listPrice;
	}


	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}


	public double getOurPrice() {
		return ourPrice;
	}


	public void setOurPrice(double ourPrice) {
		this.ourPrice = ourPrice;
	}


	public int getPageNumber() {
		return pageNumber;
	}


	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	public int getStockNumber() {
		return stockNumber;
	}


	public void setStockNumber(int stockNumber) {
		this.stockNumber = stockNumber;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public MultipartFile getProductImage() {
		return productImage;
	}


	public void setProductImage(MultipartFile productImage) {
		this.productImage = productImage;
	}

	public String getListPriceTemporary() {
		return listPriceTemporary;
	}


	public void setListPriceTemporary(String listPriceTemporary) {
		this.listPriceTemporary = listPriceTemporary;
	}


	public String getOurPriceTemporary() {
		return ourPriceTemporary;
	}


	public void setOurPriceTemporary(String ourPriceTemporary) {
		this.ourPriceTemporary = ourPriceTemporary;
	}


	public String getStockNumberTemporary() {
		return stockNumberTemporary;
	}


	public void setStockNumberTemporary(String stockNumberTemporary) {
		this.stockNumberTemporary = stockNumberTemporary;
	}

	

	
}
