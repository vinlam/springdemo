package com.service.impl.b;

import org.springframework.stereotype.Service;

import com.service.IAutoInject;

@Service
public class AutoInject2 implements IAutoInject {

	@Override
	public String print() {
		// TODO Auto-generated method stub
		System.out.println("打印b2包中的方法");
		return "打印b2包中的方法";
	}

}
