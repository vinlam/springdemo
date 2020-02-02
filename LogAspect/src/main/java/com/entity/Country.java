package com.entity;

public class Country {
	private String countryName;
	private long population;

	public Country() {
		super();
	}

	public Country(String countryName, long population) {
		super();
		this.countryName = countryName;
		this.population = population;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Long getPopulation() {
		return population;
	}

	public void setPopulation(long population2) {
		this.population = population2;
	}
}
