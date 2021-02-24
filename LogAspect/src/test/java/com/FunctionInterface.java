package com;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.function.*;

public class FunctionInterface {

	public static void main(String args[]) {
		
		// 实现用户密码 Base64加密操作
		com.function.service.Function<String,String> f01=(password)->Base64.getEncoder().encodeToString(password.getBytes());
		// 输出加密后的字符串
		System.out.println(f01.apply("123456"));
		
		
		/**
		 * 产生一个session工厂对象
		 */
		Supplier<LockTest> s = () -> {
		    return new LockTest();
		};

		s.get().printValur("123");;
		
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

//        // 菜鸟示例：start
//        // Predicate<Integer> predicate = n -> true
//        // n 是一个参数传递到 Predicate 接口的 test 方法
//        // n 如果存在则 test 方法返回 true
//        System.out.println("输出所有数据:");
//
//        // 传递参数 n
//        eval(list, n->true);
//
//        // Predicate<Integer> predicate1 = n -> n%2 == 0
//        // n 是一个参数传递到 Predicate 接口的 test 方法
//        // 如果 n%2 为 0 test 方法返回 true
//
//        System.out.println("输出所有偶数:");
//        eval(list, n-> n%2 == 0 );
//
//        // Predicate<Integer> predicate2 = n -> n > 3
//        // n 是一个参数传递到 Predicate 接口的 test 方法
//        // 如果 n 大于 3 test 方法返回 true
//
//        System.out.println("输出大于 3 的所有数字:");
//        eval(list, n-> n > 3 );
//        // 菜鸟示例: end;

		// 想不到更好的示例了，就测试一下 BiConsumer 应该一次性单独逻辑，不依赖的
//        eval2(list, (k,v) -> {System.out.println("传入的参数1为:"+k + "  传入的参数2为:"+v);});

		// 代表了一个作用于于两个同类型操作符的操作，并且返回了操作符同类型的结果
		// 逻辑处理，并返回与参数类型一样的结果
//        eval3(list, (k,v) -> v+k);

		// BiPredicate<T,U> 方法介绍:代表了一个两个参数的boolean值方法
		// 处理逻辑并返回boolean类型，可穿两个不同类型参数。
//        eval4(list, (k,v) -> v.length()==k);

		// BiFunction<T,U,R> 代表了一个接受两个输入参数的方法，并且返回一个结果
		// 三个类型都可自定义，相当于处理两个参数后返回结果。
		eval5(list, (k, v) -> v.length() == k);

	}

	public static void eval(List<Integer> list, Predicate<Integer> predicate) {
		for (Integer n : list) {
			if (predicate.test(n)) {
				System.out.println(n + " ");
			}
		}
	}

	public static void eval2(List<Integer> list, BiConsumer<Integer, String> biConsumer) {
		for (Integer n : list) {
			biConsumer.accept(n, "pa2");
		}
	}

	public static void eval3(List<Integer> list, BinaryOperator<String> binaryOperator) {
		for (Integer n : list) {
			System.out.println(binaryOperator.apply(String.valueOf(n), "abc"));
		}
	}

	public static void eval4(List<Integer> list, BiPredicate<Integer, String> biPredicate) {
		for (Integer n : list) {
			System.out.println(biPredicate.test(n, "213"));
		}
	}

	public static void eval5(List<Integer> list, BiFunction<Integer, String, Boolean> biFunction) {
		for (Integer n : list) {
			System.out.println(biFunction.apply(n, "213"));
		}
	}

}