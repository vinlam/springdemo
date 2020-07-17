package com;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.util.JsonMapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class TestDemo {
	//private static Logger log = LoggerFactory.getLogger("MyLog");
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TestDemo.class);
	private static org.apache.log4j.Logger mylog = org.apache.log4j.Logger.getLogger("myLogger");
	static {
		try {
			System.setProperty("hostName", InetAddress.getLocalHost().getHostName());
			System.setProperty("ct", DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssSSS"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public TestDemo() {
		System.setProperty("st", DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssSSS"));
	}	
	
	private static ApplicationContext applicationContext = null;
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		
		System.out.println(System.getProperty("hostName"));
		System.out.println(System.getProperty("ct"));
		System.out.println(System.getProperty("user.dir"));
		String classPath = "applicationContext-test.xml";
		//applicationContext = new ClassPathXmlApplicationContext(classPath);
		//加载config文件夹下的log4j.properties
	    String log4jPath=System.getProperty("user.dir")+"/log4j.xml";
	    log4jPath=System.getProperty("user.dir")+"/src/test/java/log4j.properties";
	    PropertyConfigurator.configure(log4jPath);
//		Log4jConfigurer.initLogging("");
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			private int count = 5;

			public void run() {
				if (count > 0) {
					//doWorkPerSecond();
					System.out.println(System.getProperty("ct"));
					System.out.println(System.getProperty("st"));
					logger.info("log4j count:"+count);
					mylog.info("mylog log4j count:"+count);
					
					count--;
				} else {
					//doWorkEnd();
					cancel();
				}
			}
		};
		timer.scheduleAtFixedRate(task, 2000L, 2000L);
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
		str = "1|12|4|5";
		String[] s = str.split("\\|");
		StringTokenizer stringTokenizer = new StringTokenizer(str, "\\|");
		List<String> sl = new ArrayList<String>();
		while(stringTokenizer.hasMoreTokens()) {
			sl.add(stringTokenizer.nextToken());
		}
		for(String c: sl) {
			System.out.println("list foreach:"+c);
		}
		
		for(int i=0;i<sl.size();i++) {
			System.out.println("list for:"+sl.get(i));
		}
		
		Iterator<String> itreator = sl.iterator();  
		while(itreator.hasNext()){  
		    System.out.println("list itreator:"+itreator.next());  
		}  
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
		String[] d = { "ab", "cd", "ef" };
		List<String> listd = new ArrayList<String>();
		Collections.addAll(listd, d);
		int pos = listd.indexOf("ef");
		StringBuilder ds = new StringBuilder("000");
		String dsd = String.valueOf(ds.charAt(pos));
		if ("0".equals(dsd)) {
			System.out.println("endpos:"+(pos + 1));
			ds.replace(pos, pos + 1, "1");
			System.out.println(ds.toString());
		}
		
		m();
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
		test(list1, list2);
		test1(list1, list2);
		test2(list1, list2);
		test3(list1, list2);
		
		List<String> mlist1 = new ArrayList<String>();
		mlist1.add("A");
		mlist1.add("D");
		mlist1.add("B");
		mlist1.add("C");

		List<String> mlist2 = new ArrayList<String>();
		mlist2.add("C");
		mlist2.add("B");
//		mlist2.add("E");
		//mlist1.removeAll(mlist2);
		
		List<String> mlist3 = new ArrayList<String>();
		mlist3 = mlist1;
		//mlist2.addAll(mlist1);
		//mlist2.addAll(mlist1);
		mlist2.retainAll(mlist1);//交集
		mlist1.removeAll(mlist2);//删除交集
		mlist2.addAll(mlist1);//用排序后的数据添加删除交集后剩下的数据 结果应为：CBAD
		System.out.println("mlist求并集(去重):"+mlist2);
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
	
//	java 与 或 非 异或 & | ~ ^
//	1．与运算符 &
//	两个操作数中位都为1，结果才为1，否则结果为0
//	
//	2．或运算符 |
//	两个位只要有一个为1，那么结果就是1，否则就为0
//
//	3．非运算符 ~
//	如果位为0，结果是1，如果位为1，结果是0
//
//	4．异或运算符 ^
//	两个操作数的位中，相同则结果为0，不同则结果为1
	private static void m() {
//		一.原码
//		1>.正数的原码就是它的本身
//		　　假设使用一个字节存储整数，整数10的原码是：0000 1010
//
//		2>.负数用最高位是1表示负数
//		　　假设使用一个字节存储整数，整数-10的原码是：1000 1010
//
//		二.反码
//		1>.正数的反码跟原码一样
//		　　假设使用一个字节存储整数，整数10的反码是：0000 1010
//
//		2>.负数的反码是负数的原码按位取反（0变1,1变0），符号位不变
//		　　假设使用一个字节存储整数，整数-10的反码是：1111 0101
//
//		三.补码（再次强调，整数的补码才是在计算机中的存储形式。）
//		1>.正数的补码和原码一样
//		　　假设使用一个字节存储整数，整数10的补码是：0000 1010（第三次强调：这一串是10这个整数在计算机中存储形式）
//
//		2>.负数的补码是负数的反码加1
//		　　假设使用一个字节存储整数，整数-10的补码是：1111 0110（第三次强调：这一串是-10这个整数在计算机中存储形式）
//
//		四.在计算机中，为什么不用原码和反码，而是用补码呢？
//		　　因为在使用原码，反码在计算时不准确，使用补码计算时才准确。
//
//		1>.使用原码计算10-10
//		　　　    0000 1010　　（10的原码）
//		　   +   1000 1010 　 （-10的原码）
//		------------------------------------------------------------
//		　　　　   1001 0100　　（结果为：-20，很显然按照原码计算答案是否定的。）
//
//		2>.使用反码计算10-10
//		　　　　　　0000 1010　　（10的反码）
//		　　　+　  1111 0101　　（-10的反码）
//		------------------------------------------------------------
//		　　　　　　1111 1111　　（计算的结果为反码，我们转换为原码的结果为：1000 0000，最终的结果为：-0，很显然按照反码计算答案也是否定的。）
//
//		3>.使用补码计算10-10
//		　　　　　　0000 1010　　（10的补码）
//		　　　+　　 1111  0110　　（-10的补码）
//		------------------------------------------------------------
//		　　　　  1 0000 0000　　（由于我们这里使用了的1个字节存储，因此只能存储8位，最高位（第九位）那个1没有地方存，就被丢弃了。因此，结果为：0）
//
//		五.小试牛刀
//		　　有了上面的案例，接下来，我们来做几个小练习吧，分别计算以下反码表示的十进制数字是多少呢？
//		1>.0b0000 1111
//		　　相信这个数字大家异口同声的就能说出它的答案是：15（因为正数的补码和原码一样）
//
//		2>.0b1111 1111
//		　　计算过程：0b1111 1111（补码）------>0b1111 1110（反码）------>0b1000 0001（原码）
//		　　将其换算成原码之后就可以得到最后的结果为：-1
//
//		3>.0b1111 0000
//		　　计算过程：0b1111 0000（补码）------>0b1110 1111（反码）------>0b10010000(原码)
//		　　将其换算成原码之后就可以得到最后的结果为：-16
//
//		4>.0b1000 0001
//		　　计算过程：0b1000 0001（补码）------>0b1000 0000（反码）------->0b1111 1111（原码）
//		　　将其换算成原码之后就可以得到最后的结果为：-127
		int a=128;
		int b=129;
		System.out.println(a&b);//128;a 的值是129，转换成二进制就是10000001，而b 的值是128，转换成二进制就是10000000。根据与运算符的运算规律，只有两个位都是1，结果才是1，可以知道结果就是10000000，即128。
		System.out.println(a|b);//129;a 的值是129，转换成二进制就是10000001，而b 的值是128，转换成二进制就是10000000，根据或运算符的运算规律，只有两个位有一个是1，结果才是1，可以知道结果就是10000001，即129。
		a=2;//0010 
		//变量a的二进制数形式：        00000000 00000000 00000000 00000010
		//逐位取反后，等于十进制的-3：  11111111 11111111 11111111 11111101 变成负数
		//补码 10000000 00000000 00000000 00000010 | 00000000 00000000 00000000 00000001 | 100000000 00000000 00000000 00000011
		
		System.out.println("a 非的结果是："+(~a));	//-3
		a=10;//1010
		//00000000 00000000 00000000 00001010
		//11111111 11111111 11111111 11110101
		//10000000 00000000 00000000 00001010 | 00000000 00000000 00000000 00000001 | 10000000 00000000 00000000 00001011
		
		System.out.println("a 非的结果是："+(~a));	//-11
		
		a=15;
		b=2;
		System.out.println("a 与 b 异或的结果是："+(a^b));//a 与 b 异或的结果是：13 分析上面的程序段：a 的值是15，转换成二进制为1111，而b 的值是2，转换成二进制为0010，根据异或的运算规律，可以得出其结果为1101 即13。
	}
}
