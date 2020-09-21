package com;

public class EnumTest {
	public static void main(String[] args) {
		System.out.println(EnumDemo.CODE.getName());
		System.out.println(EnumDemo.getItem(2).getName());
		System.out.println(Type.valueOf("T"));
	}
}

 enum Type{
	R(1),
	D(2);
	Type(int i) {
	}
	 
}