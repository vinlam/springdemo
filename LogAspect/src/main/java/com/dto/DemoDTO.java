package com.dto;

import java.math.BigDecimal;
import java.util.Date;

public class DemoDTO{
	private BigDecimal bigDecimal;
	private String data;
	private Integer integer;
	private Double double1;
	private Long long1;
	private long l;
	private Date date;
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
	public Integer getInteger() {
		return integer;
	}
	public void setInteger(Integer integer) {
		this.integer = integer;
	}
	public Double getDouble1() {
		return double1;
	}
	public void setDouble1(Double double1) {
		this.double1 = double1;
	}
	public Long getLong1() {
		return long1;
	}
	public void setLong1(Long long1) {
		this.long1 = long1;
	}
	public long getL() {
		return l;
	}
	public void setL(long l) {
		this.l = l;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}