package com.service.impl;

import com.service.ProxyInterface;

public class RealObject implements ProxyInterface {

	@Override
	public void doSomething() {
		// TODO Auto-generated method stub
		System.out.println("do something");
	}

	@Override
	public void somethingElse(String arg) {
		// TODO Auto-generated method stub
		System.out.println("do else thing:"+arg);
	}

}
