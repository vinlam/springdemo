package com.entity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Order {
	@NotEmpty
	private String orderNo;
	
	@NotNull
	@Valid
	private OrderItem orderItem;
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public OrderItem getOrderItem() {
		return orderItem;
	}
	
	public void setOrderItem(OrderItem item) {
		this.orderItem = item;
	}
}
