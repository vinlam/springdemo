package com;

import java.util.HashSet;

public class HashCodeTest {
	public static void main(String[] args) {
		Cat c1 = new Cat("tom", "blue");
		Cat c2 = new Cat("tom", "blue");
		Cat c3 = new Cat("jack", "yellow");
		Cat c4 = new Cat("TOM", "blue");
		
		HashSet<Cat> set = new HashSet<Cat>();
		set.add(c1);
		set.add(c2);
		set.add(c3);
		set.add(c4);
		
//	    1)、如果两个对象相等，那么它们的hashcode()值一定相同；
//		 c1.equals(c2) : true;(hashcode 值）c1 == c2:true 
//		 2)、如果两个对象的hashcode()相等，那么它们并不一定相等 equals (哈希冲突)；
//		 c1.equals(c4) : false;(hashcode 值）c1 == c4:true 
//		 c3(-633357258) 
//		set:[jack - yellow, tom - blue, TOM - blue] 
		
		System.out.println("1)、如果两个对象相等，那么它们的hashcode()值一定相同；");
		System.out.printf(" c1.equals(c2) : %s;(hashcode 值）c1 == c2:%s \n ",c1.equals(c2),c1.hashCode() == c2.hashCode());
		System.out.println("2)、如果两个对象的hashcode()相等，那么它们并不一定相等 equals (哈希冲突)；");
		System.out.printf(" c1.equals(c4) : %s;(hashcode 值）c1 == c4:%s \n ",c1.equals(c4),c1.hashCode() == c4.hashCode());
		System.out.printf("c3(%d) \n",c3.hashCode());
		System.out.printf("set:%s \n",set);
		
//		如果没有重写hashcode 及 equals 则结果如下：
//		1)、如果两个对象相等，那么它们的hashcode()值一定相同；
//		 c1.equals(c2) : false;(hashcode 值）c1 == c2:false 
//		 2)、如果两个对象的hashcode()相等，那么它们并不一定相等 equals (哈希冲突)；
//		 c1.equals(c4) : false;(hashcode 值）c1 == c4:false 
//		 c3(1311053135) 
//		set:[tom - blue, TOM - blue, tom - blue, jack - yellow] 
	}
}
