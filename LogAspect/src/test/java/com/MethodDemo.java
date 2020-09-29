package com;

public class MethodDemo {
	public byte[] read_status(Object arg) {
		byte[] test = new byte[10];
		for (int i = 0; i < 10; i++) {
			test[i] = (byte) i;
		}
		return test;
	}

	public int test(String s) throws Exception {
		Object r = this.getClass().getDeclaredMethod("read_status").invoke(this, (Object) s);
		byte[] bytes = (byte[]) r;
		for (int i = 0; i < bytes.length; i++) {
			System.out.println("" + bytes[i]);
		}
		return 0;
	}

	public int test1(String s) throws Exception {
		Object r = this.getClass().getDeclaredMethod("read_status", Object.class).invoke(this, (Object) s);
		byte[] bytes = (byte[]) r;
		for (int i = 0; i < bytes.length; i++) {
			System.out.println("" + bytes[i]);
		}
		return 0;
	}
	
	public static void main(String[] args) throws Exception {
		MethodDemo m = new MethodDemo();
		
		m.test1("123123");
		m.test("asdf");
	}
}
