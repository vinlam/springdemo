package com.service.impl.a;

import org.springframework.stereotype.Service;

import com.service.IAutoInject;

@Service("AutoInjectA")
public class AutoInject implements IAutoInject {

	@Override
	public String print() {
		// TODO Auto-generated method stub
		System.out.println("打印a包中的方法");
		return "打印a包中的方法";
	}

}
