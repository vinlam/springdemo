package com;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;


public class checkElementContains {
	public static void main(String[] args) {
	    String[] arr = new String[] {  "CD",  "BC", "EF", "DE", "AB"};

	    //use list
	    long startTime = System.nanoTime();
	    for (int i = 0; i < 100000; i++) {
	        useList(arr, "A");
	    }
	    long endTime = System.nanoTime();
	    long duration = endTime - startTime;
	    System.out.println("useList:  " + duration / 1000000);

	    //use set
	    startTime = System.nanoTime();
	    for (int i = 0; i < 100000; i++) {
	        useSet(arr, "A");
	    }
	    endTime = System.nanoTime();
	    duration = endTime - startTime;
	    System.out.println("useSet:  " + duration / 1000000);

	    //use loop
	    startTime = System.nanoTime();
	    for (int i = 0; i < 100000; i++) {
	        useLoop(arr, "A");
	    }
	    endTime = System.nanoTime();
	    duration = endTime - startTime;
	    System.out.println("useLoop:  " + duration / 1000000);

	    //use Arrays.binarySearch()
	    startTime = System.nanoTime();
	    for (int i = 0; i < 100000; i++) {
	        useArraysBinarySearch(arr, "A");
	    }
	    endTime = System.nanoTime();
	    duration = endTime - startTime;
	    System.out.println("useArrayBinary:  " + duration / 1000000);
	}
	
	//检查数组是否包含某个值的方法
	//使用List
	public static boolean useList(String[] arr, String targetValue) {
	    return Arrays.asList(arr).contains(targetValue);
	}
	
	//使用Set
	public static boolean useSet(String[] arr, String targetValue) {
	    Set<String> set = new HashSet<String>(Arrays.asList(arr));
	    return set.contains(targetValue);
	}
	
	//使用循环判断
	public static boolean useLoop(String[] arr, String targetValue) {
		int i = 0;
	    for(String s: arr){
	    	i++;
	        if(s.equals(targetValue)){
	        	System.out.println("loop:" + i);
	            return true;
	        }
	    }
	    return false;
	}
	
	//	使用 Arrays.binarySearch()
	//	Arrays.binarySearch()方法只能用于有序数组！！！如果数组无序的话得到的结果就会很奇怪。
	//如果使用 Arrays.binarySearch() 方法，数组必须是已排序的。由于上面的数组并没有进行排序，所以该方法不可使用
	public static boolean useArraysBinarySearch(String[] arr, String targetValue) { 
	    int a =  Arrays.binarySearch(arr, targetValue);
	    if(a > 0){
	    	System.out.println("index:" + a);
	        return true;
	    }else{
	        return false;
	    }
	}
	
	public static boolean useArrayUtils(String[] arr, String targetValue) {
	    return ArrayUtils.contains(arr,targetValue);
	}
}
