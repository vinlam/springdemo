package com.entity;

public class BaiduIpContent {
	private String address;
	private BaiduIpAddressDetail address_detail;
	private BaiduIpPoint point;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public BaiduIpAddressDetail getAddress_detail() {
		return address_detail;
	}
	public void setAddress_detail(BaiduIpAddressDetail address_detail) {
		this.address_detail = address_detail;
	}
	public BaiduIpPoint getPoint() {
		return point;
	}
	public void setPoint(BaiduIpPoint point) {
		this.point = point;
	}
}
