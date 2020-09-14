package com.eunm;

public /**
		 * 适配策略枚举
		 */
enum StrategyCrossEnums {
	STRATEGY_ONE("1", "userService1"),

	STRATEGY_TWO("2", "userService2"),;

	private String code;

	private String className;

	StrategyCrossEnums(String code, String className) {
		this.code = code;
		this.className = className;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
