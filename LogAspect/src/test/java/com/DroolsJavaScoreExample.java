package com;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.entity.DroolsOrder;
import com.entity.DroolsUser;

public class DroolsJavaScoreExample {
	public static void main(String[] args) throws Exception {  
        
        List<DroolsOrder> orderList = getInitData();
        for (int i=0; i<orderList.size(); i++){  
        	DroolsOrder order = orderList.get(i);  
            if (order.getAmout() <= 100){  
                order.setScore(0);  
                addScore(order);  
            }else if(order.getAmout() > 100 && order.getAmout() <= 500){  
                order.setScore(100);  
                addScore(order);  
            }else if(order.getAmout() > 500 && order.getAmout() <= 1000){  
                order.setScore(500);  
                addScore(order);  
            }else{  
                order.setScore(1000);  
                addScore(order);  
            }  
        }  
          
    }  
      
    private static void addScore(DroolsOrder o){  
        System.out.println("用户" + o.getUser().getName() + "享受额外增加积分: " + o.getScore());  
    }  
      
    private static List<DroolsOrder> getInitData() throws Exception {  
        List<DroolsOrder> orderList = new ArrayList<DroolsOrder>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        {
        	DroolsOrder order = new DroolsOrder();  
            order.setAmout(80);  
            order.setBookingDate(df.parse("2015-07-01"));  
            DroolsUser user = new DroolsUser();
            user.setLevel(1);  
            user.setName("Name1");  
            order.setUser(user);  
            orderList.add(order);  
        }
        {
        	DroolsOrder order = new DroolsOrder();  
            order.setAmout(200);  
            order.setBookingDate(df.parse("2015-07-02"));  
            DroolsUser user = new DroolsUser();
            user.setLevel(2);  
            user.setName("Name2");  
            order.setUser(user);  
            orderList.add(order);  
        }  
        {  
        	DroolsOrder order = new DroolsOrder();  
            order.setAmout(800);  
            order.setBookingDate(df.parse("2015-07-03"));  
            DroolsUser user = new DroolsUser();  
            user.setLevel(3);  
            user.setName("Name3");  
            order.setUser(user);  
            orderList.add(order);  
        }  
        {  
        	DroolsOrder order = new DroolsOrder();  
            order.setAmout(1500);  
            order.setBookingDate(df.parse("2015-07-04"));  
            DroolsUser user = new DroolsUser();  
            user.setLevel(4);  
            user.setName("Name4");  
            order.setUser(user);  
            orderList.add(order);  
        }  
        return orderList;  
    }  
}
