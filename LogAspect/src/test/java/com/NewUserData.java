package com;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewUserData {
	//@JsonProperty("UW")
	private String weight;
	//@JsonProperty("UH")
	private String height;
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
}
