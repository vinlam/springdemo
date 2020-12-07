package com.entry.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Address {

    @XStreamAlias("_Country")
    private String country;

    @XStreamAlias("_Province")
    private String province;

    @XStreamAlias("_City")
    private String city;

	public Address(String country, String province, String city) {
		this.country = country;
		this.province = province;
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String toString() {
		// TODO Auto-generated method stub
		return "Address{" + "country='" + country + '\'' + ", province='" + province + '\'' + ", city=" + city +'}';
	}
}
