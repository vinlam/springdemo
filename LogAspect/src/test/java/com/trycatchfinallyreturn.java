package com;

import java.util.HashMap;
import java.util.Map;

public class trycatchfinallyreturn {
    public static void main(String[] args) {
		System.out.println(getMap().get("key"));//try 里面没return 则返回 null 有则的返回finally里面的finally,是因为finally能够修改返回值所引用的对象内容，但不能改变实际的返回
        System.out.println(setOne().toString());
        System.out.println(getInt(8));//8
        
        //finally 修改基本类型，不影响返回值。修改非基本类型，影响返回值
    }

	private static Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("key", "init");
        try {
        	map.put("key", "try");
        	System.out.println("1");
        	Long n = null;
        	//System.out.println(n.toString());
        	return map;
        }catch (Exception e) {
			// TODO: handle exception
        	map.put("key", "catch");
        	System.out.println("2");
        	e.printStackTrace();
		}finally {
			map.put("key", "finally");
			map = null;
			System.out.println("3");
			//System.out.println(map.get("key"));//return null pointer exception
		}
        System.out.println("4");
		return map;
	}
	
	private static StringBuilder setOne() {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("cool");
			return sb.append("return");
		} finally {
			//sb = null;//return null pointer exception
			sb.append("Of course ,the finally block could modify the contents the object referred to by teh reutrn value - for example: \nfinally {\nsb.append(\"Of course ,the finally block could modify the contents the object referred to by teh reutrn value\"\n)");
		}
	}
	
	private static int getInt(int i) {
		try {
			System.out.println("111" + i);
		    return i;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("222");
			e.printStackTrace();
		}finally {
			i = 2;
			System.out.println("333");
		}
		System.out.println("444");
		return i;
	}
}
