package com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;

public class CompareDemo {
	public static void main(String[] args) {
		List<NewsItem> list=new ArrayList<>();
		list.add(new NewsItem("东方饭店深V",1000,new Date()));
		list.add(new NewsItem("对方水电费",100,new Date()));
		list.add(new NewsItem("的范德萨发放",89,new Date(System.currentTimeMillis()-1000*60*60)));
		System.out.println("排序前:"+list);
		//排序
		list.sort(null);
		System.out.println("排序之后：");
		System.out.println("排序之后："+list);
		
		System.out.println("实体类对象："+new NewsItem("东方饭店深V",1000,new Date()));
		
		List<Goods> list2=new ArrayList<>();
		list2.add(new Goods("佛挡杀佛",100,100));
		list2.add(new Goods("辅导费",103,10));
		list2.add(new Goods("辅导风",105,80));
		list2.add(new Goods("如果",106,900));
		System.out.println("排序之前："+list);
		//排序
		list2.sort(new GoodsPriceComp());
		System.out.println("排序之后："+list2);
		sort(list2,"fav",true);
		System.out.println("排序之后2："+list2);
	}
	
	/**
	 * bean的集合按照指定bean的字段排序
	 * @param <T>
	 * @param list 要排序的集合
	 * @param filedName 字段名称
	 * @param ascFlag 是否升序
	 */
	public static <T> void sort(List<T> list, String filedName, boolean ascFlag) {
        if (list.size() == 0 || filedName.equals("")) {
            return;
        }
        Comparator<?> cmp = ComparableComparator.getInstance();
        if (ascFlag) {
            cmp = ComparatorUtils.nullLowComparator(cmp);
        } else {
            cmp = ComparatorUtils.reversedComparator(cmp);

        }
        Collections.sort(list, new BeanComparator<T>(filedName, cmp));
    }
}
