package com;

public enum EnumDemo {
	CODE(1, "RSA"),
	LEARN(2, "RSA"), 
	CONTRIBUTE(3, "RSA"), 
	QUIZ(4, "RSA"), 
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

}
