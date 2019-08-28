package com.entity;
/**
 * 经纬度
 */
public class LocationPosition{
	private int id;//id  代表商家
	private double Latitude;//纬度
	private double Longitude;//经度
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLatitude() {
		return Latitude;
	}
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}
	public double getLongitude() {
		return Longitude;
	}
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	public LocationPosition(double latitude, double longitude) {
		super();
		Latitude = latitude;
		Longitude = longitude;
	}
	public LocationPosition(int id, double latitude, double longitude) {
		super();
		this.id = id;
		Latitude = latitude;
		Longitude = longitude;
	}
}