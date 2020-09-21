package com;

public enum EnumDemo {
	CODE(1, "RSA"),
	LEARN(2, "DES"), 
	CONTRIBUTE(3, "AES"), 
	QUIZ(4, "MD5"), 
	MCQ(5, "RSA");

	private int type;
	private String name;

	private EnumDemo(int type, String name) {
		this.setType(type);
		this.setName(name);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	static EnumDemo getItem(int type) {
		for(EnumDemo enumDemo:EnumDemo.values()) {
			if(enumDemo.getType() == type) {
				return enumDemo;
			}
		}
		return null;
	}

}
