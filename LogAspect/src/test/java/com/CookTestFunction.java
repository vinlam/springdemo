package com;

import com.function.service.Cook;

public class CookTestFunction {
    public static void main(String[] args) {
        // 调用invokeCook()方法 传递Cook接口的匿名内部类对象
        invokeCook(new Cook() {
            @Override
            public void makeFood() {
                System.out.println("开饭了");
            }
        });

        // 使用Lambda表达式简化匿名内部类的书写
        invokeCook(() -> {
            System.out.println("inner class开饭了");
        });
    }

    // 定义一个方法 参数传递Cook接口 方法内部调用Cook接口中的makeFood()方法
    public static void invokeCook(Cook cook)
    {
        cook.makeFood();
    }
}