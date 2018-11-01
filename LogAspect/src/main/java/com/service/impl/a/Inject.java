package com.service.impl.a;

import org.springframework.stereotype.Service;

import com.service.IAutoInject;

@Service("Inject")
public class Inject implements IAutoInject {

	@Override
	public String print() {
		// TODO Auto-generated method stub
		System.out.println("打印没有name声明的Inject的方法");
		return "打印没有name声明的Inject的方法";
	}

}
