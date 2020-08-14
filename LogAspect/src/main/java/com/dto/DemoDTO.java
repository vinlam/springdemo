package com.dto;

import java.math.BigDecimal;

public class DemoDTO{
	private BigDecimal bigDecimal;
	private String data;
	public BigDecimal getBigDecimal() {
		return bigDecimal;
	}
	public void setBigDecimal(BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}