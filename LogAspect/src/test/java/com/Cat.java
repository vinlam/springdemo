package com;

import java.util.Objects;

public class Cat {
	private String name;
	private String color;
	
	public Cat(String name,String color) {
		this.name = name;
		this.color = color;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		//return super.hashCode();
		return Objects.hash(name.toLowerCase(),color);
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		//return super.equals(obj);
		if(this == obj) {
			return true;
		}
		
		if(obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		Cat c = (Cat) obj;
		return Objects.equals(color, c.color) && Objects.equals(name, c.name);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + " - " + color;
	}
}
