package com.service.impl.b;

import org.springframework.stereotype.Service;

import com.service.IAutoInject;

@Service("AutoInjectB")
public class AutoInject implements IAutoInject {

	@Override
	public String print() {
		// TODO Auto-generated method stub
		System.out.println("打印b包中的方法");
		return "打印b包中的方法";
	}

}
