package com.entity;

public class BaiduIpResponse {
	private String address;
	private BaiduIpContent content;
	private Integer status;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public BaiduIpContent getContent() {
		return content;
	}
	public void setContent(BaiduIpContent content) {
		this.content = content;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
