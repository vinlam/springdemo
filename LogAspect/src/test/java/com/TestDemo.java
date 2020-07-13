package com;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.util.JsonMapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class TestDemo {
	public static void main(String[] args) throws ClassNotFoundException, IOException {

		String str = "thi is a test 这是一个测试";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<persons>\n" + " <person id=\"23\">\n"
				+ " <name>张 三</name>\n" + " <age>26</age>\n" + "</person>\n" + " <person id=\"22\">\n"
				+ " <name>李四</name>\n" + " <age>25</age>\n" + " </person>\n" + "</persons>";
		System.out.println("用escapeJava方法转义之后的字符串为:" + StringEscapeUtils.escapeJava(str));
		System.out.println(
				"用unescapeJava方法反转义之后的字符串为:" + StringEscapeUtils.unescapeJava(StringEscapeUtils.escapeJava(str)));
		System.out.println("用escapeHtml方法转义之后的字符串为:" + StringEscapeUtils.escapeHtml(str));
		System.out.println(
				"用unescapeHtml方法反转义之后的字符串为:" + StringEscapeUtils.unescapeHtml(StringEscapeUtils.escapeHtml(str)));
		System.out.println("用escapeXml方法转义之后的字符串为:" + StringEscapeUtils.escapeXml(xml));
		System.out
				.println("用unescapeXml方法反转义之后的字符串为:" + StringEscapeUtils.unescapeXml(StringEscapeUtils.escapeXml(xml)));
		System.out.println("用escapeJavaScript方法转义之后的字符串为:" + StringEscapeUtils.escapeJavaScript(str));
		System.out.println("用unescapeJavaScript方法反转义之后的字符串为:"
				+ StringEscapeUtils.unescapeJavaScript(StringEscapeUtils.unescapeJavaScript(str)));
		compare();

		// int[] a = {1,2,4,5,3};
		int[] a = { 1, 2, 4, 5 };
		List<User> list = new ArrayList<User>();
		List<User> newlist = new ArrayList<User>();
		User u1 = new User(1, "q", 5);
		User u2 = new User(2, "j", 7);
		User u3 = new User(3, "g", 10);
		User u4 = new User(4, "t", 8);
		User u5 = new User(5, "y", 6);
		list.add(u1);
		list.add(u2);
		list.add(u3);
		list.add(u4);
		list.add(u5);
		for (int i : a) {
			for (User u : list) {
				if (u.getId() == i) {
					newlist.add(u);
				}
			}
		}
		list = newlist;
		list = new ArrayList<>(Arrays.asList(new User[newlist.size()]));

		Collections.copy(list, newlist);

		list = deepCopy(newlist); // 调用该方法

		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

		mapperFactory.classMap(User.class, Map.class).byDefault();
		MapperFacade mapper = mapperFactory.getMapperFacade();
		list = mapper.mapAsList(newlist, User.class);

		// System.arraycopy(newlist, 0, list, 0, newlist.size());
		System.out.println(JsonMapper.toJsonString(list));
		System.out.println(JsonMapper.toJsonString(newlist));
		String[] s = "1|12|4|5".split("\\|");
		for (String n : s) {
			if (Long.valueOf(n).longValue() == 1L) {
				System.out.println(n);
				System.out.println(Long.valueOf(n).equals(1L));
				System.out.println(n.equals(1));
				System.out.println(n.equals(String.valueOf(1L)));

			}
		}

		String ss = "1000000000";
		System.out.println(ss.charAt(0));// 1
		System.out.println(ss.charAt(1));// 0
		String item = "1111111111";
		char[] status = item.toCharArray();

		status[6] = '6';
		status[7] = '6';
		// status[8] = '6';

		ss = Arrays.toString(status).replaceAll("[\\[\\]\\s,]", "");
		System.out.println(ss);// 1111116611

		str = "****";
		if (StringUtils.isNotBlank(str)) {
			StringBuilder sb = new StringBuilder("18698587234");
			sb.replace(3, 7, str);
			System.err.println("========" + sb.toString());// ========186****7234
		}
	}

	public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
		outputStream.writeObject(src);

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);
		List<T> dest = (List<T>) inputStream.readObject();

		if (outputStream != null) {
			outputStream.close();
		}
		if (inputStream != null) {
			inputStream.close();
		}
		return dest;
	}

	private static void compare() {
		List<String> orderings = Stream.of("温度", "运行时间").collect(Collectors.toList());
		List<String> target = Stream.of("运行时间ss", "运行时间", "3", "温度1", "温度").collect(Collectors.toList());

		class ExplicitOrdering<T> implements Comparator<T> {

			private Map<T, Integer> indexMap = new HashMap<T, Integer>();

			public ExplicitOrdering(List<T> explicit) {
				for (int i = 0; i < explicit.size(); i++) {
					indexMap.put(explicit.get(i), i);
				}
			}

			@Override
			public int compare(T o1, T o2) {
				return rank(o1) - rank(o2);
			}

			private int rank(T value) {
				Integer rank = indexMap.get(value);
				if (rank == null) {
					return Integer.MIN_VALUE;
				}
				return rank;
			}
		}

		List<String> strings = target.stream()
				.sorted(new ExplicitOrdering<>(orderings).thenComparing(Comparator.naturalOrder()))
				.collect(Collectors.toList());

		System.out.println(strings);
		listDemo();
	}

	private static void streamDemo() {

		List<User> users = new ArrayList<>();
		users.add(new User(1, "A", 8));
		users.add(new User(2, "A", 7));
		users.add(new User(3, "A", 6));
		users.add(new User(4, "B", 7));
		users.add(new User(5, "B", 6));
		users.add(new User(6, "C", 6));
		users.add(new User(6, "C", 6));

		/**
		 * 1，跟据某个属性(name)分组
		 **/
		// Map<String, List<User>> collectName =
		// users.stream().collect(Collectors.groupingBy(User::getName));
		// System.out.println(collectName);

		/**
		 * 2，根据某个属性(name)分组，求age的和
		 **/
		// Map<String, Integer> collectNameSumAge = users.stream()
		// .collect(Collectors.groupingBy(User::getName,
		// Collectors.summingInt(User::getAge)));
		// System.out.println(collectNameSumAge);

		/**
		 * 3，取出一组对象的某个属性(name)组成一个新集合
		 */
		// List<String> names =
		// users.stream().map(User::getName).collect(Collectors.toList());
		// System.out.println(names);

		/**
		 * 4，list去重复
		 */
		// names = names.stream().distinct().collect(Collectors.toList());
		// System.out.println(names);

		users = users.stream().distinct().collect(Collectors.toList());
		System.out.println(users);

		/**
		 * 5，根据某个属性(name)添加条件过滤数据
		 **/
		// users = users.stream().filter(user ->
		// user.getName().equals("A")).collect(Collectors.toList());
		// System.out.println(users);

		/**
		 * 6，判断一组对象里面有没有属性值是某个值
		 */
		// boolean checkName = names.contains("A");
		// System.out.println(checkName);

		// boolean bool = users.stream().anyMatch(user -> "A".equals(user.getName()));
		// System.out.println(bool);
	}

	private static void listDemo() {
		List<String> list1 = new ArrayList<String>();
		list1.add("A");
		list1.add("B");

		List<String> list2 = new ArrayList<String>();
		list2.add("B");
		list2.add("C");
		test(list1,list2);
		test1(list1,list2);
		test2(list1,list2);
		test3(list1,list2);
	}

//0.求差集
//例如，求List1中有的但是List2中没有的元素:

	public static void test3(List list1, List list2) {
		list1.removeAll(list2);
		System.out.println(list1);
	}
//结果:[A]

//查看ArrayList的removeAll的源码
//	public boolean removeAll(Collection<?> c) {
//		Objects.requireNonNull(c);
//		return batchRemove(c, false);
//	}

//再查看batchRemove的源码:(如果传的第二个参数是false，保留差集;如果传的是true，保留的是交集)
//private boolean batchRemove(Collection<?> c, boolean complement) {
//    final Object[] elementData = this.elementData;
//    int r = 0, w = 0;
//    boolean modified = false;
//    try {
//        for (; r < size; r++)
//            if (c.contains(elementData[r]) == complement)
//                elementData[w++] = elementData[r];
//    } finally {
//        // Preserve behavioral compatibility with AbstractCollection,
//        // even if c.contains() throws.
//        if (r != size) {
//            System.arraycopy(elementData, r,
//                             elementData, w,
//                             size - r);
//            w += size - r;
//        }
//        if (w != size) {
//            // clear to let GC do its work
//            for (int i = w; i < size; i++)
//                elementData[i] = null;
//            modCount += size - w;
//            size = w;
//            modified = true;
//        }
//    }
//    return modified;
//}

//是重新定义elementData数组的元素，下面代码的作用是将本集合中不包含另一个集合的元素重新加入元素，以此实现删除的功能(注意上面调用的方法传的参数是false，也就是不包含的元素得以保留,实现差集的功能)
//if (c.contains(elementData[r]) == complement)
//elementData[w++] = elementData[r];

//1.求并集(不去重)---将一个集合全部加入另一个集合
	public static void test(List list1, List list2) {
		list1.addAll(list2);
		System.out.println(list1);
	}
//结果:[A, B, B, C]

//查看ArayList的addAll()源码是数组复制:
//	public boolean addAll(Collection<? extends E> c) {
//		Object[] a = c.toArray();
//		int numNew = a.length;
//		ensureCapacityInternal(size + numNew); // Increments modCount
//		System.arraycopy(a, 0, elementData, size, numNew);
//		size += numNew;
//		return numNew != 0;
//	}

//再查看System的arraycopy发现是一个native方法(本地方法):---其实system类的好多方法都是native方法
	// public static native void arraycopy(Object src, int srcPos, Object dest, int
	// destPos, int length);

//2.求并集(去重)
//例如:求List1和List2的并集,并实现去重。
//思路是:先将list中与list2重复的去掉，之后将list2的元素全部添加进去。
	public static void test1(List list1, List list2) {
		list1.removeAll(list2);
		list1.addAll(list2);
		System.out.println(list1);
	}
//结果:[A, B, C]

//3.求交集
//例如:求List1和List2中都有的元素。
	public static void test2(List list1, List list2) {
		list1.retainAll(list2);
		System.out.println(list1);
	}
//结果：[B]

//在上面2的实验过程中，我们知道batchRemove(Collection,true)也是求交集，所以猜想  retainAll 内部应该是调用 batchRemove(Collection,true)，查看ArrayList的源码如下:
//	public boolean retainAll(Collection<?> c) {
//		Objects.requireNonNull(c);
//		return batchRemove(c, true);
//	}
}
