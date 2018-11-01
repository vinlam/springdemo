package com.entity;
public class Product {
	private String name;
	private long id;
	private String productClass;
	private String productId;
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

}
