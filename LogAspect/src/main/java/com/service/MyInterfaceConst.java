package com.service;

//接口当中常量的定义
public interface MyInterfaceConst {

	//注意：
	//一旦使用final关键字进行修饰，说明赋值之后不可改变。
	//1.接口当中的常量，可以省略public static final，不管是写还是不写，实质都是一样的。
	//2.接口中的常量，必须进行赋值，不能不赋值。
	//3.接口中常量的名称，使用完全大写的字母，用下划线进行分隔（推荐）
	//这是一个常量，一旦赋值，之后不可以修改
	public static final int NUM_OF_MY_CODE = 10;// 如果不赋值默认是0
}
