package com.entity;
public class Product {
	private String name;
	private long id;
	private String productClass;
	private String productId;
	
	private String price;
	
	private String detail;

	public Product(String id, String name, String price, String detail) {
		this.id = Long.valueOf(id);
		this.name = name;
		this.price = price;
		this.detail = detail;
	}

	public Product() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getProductClass() {
		return productClass;
	}

	public String getProductId() {
		return productId;
	}

	
	public void setName(String name) {
		this.name = name;
	}


	public void setProductClass(String productClass) {
		this.productClass = productClass;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}


}
