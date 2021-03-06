package com;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.dto.DemoDTO;
import com.util.JsonMapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class TestDemo {
	// private static Logger log = LoggerFactory.getLogger("MyLog");
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
//	验证规则说明：
//	（1）验证http,https,ftp开头
//	（2）验证一个":"，验证多个"/"
//	（3）验证网址为 xxx.xxx
//	（4）验证有0个或1个问号
//	（5）验证参数必须为xxx=xxx格式，且xxx=空    格式通过
//	（6）验证参数与符号&连续个数为0个或1个
	public static void reg() {
		String url = "http:/klsfnklnklwnl.csfwfwn.cn?1231=sjkfjkf&sfwfw=";
		String regex = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
		Pattern pattern = Pattern.compile(regex);
		if (pattern.matcher(url).matches()) {
			System.out.println("是正确的网址");
		} else {
			System.out.println("非法网址");
		}
		
		String reg = "^(?:https?://)?[\\w]{1,}(?:\\.?[\\w]{1,})+[\\w-_/?&=#%:]*$";
		String reg1 = "^(http|https)?:\\/\\/([\\w]{1,}+\\.)*(cb.com)*$";
		url = "https://a.cb.com";
		pattern = Pattern.compile(reg1);
		if (pattern.matcher(url).matches()) {
			System.out.println("是正确的网址");
		} else {
			System.out.println("非法网址");
		}
		
		//1、(?!)表示整体忽略大小写，如果单个，则可以写成"^d(?!)oc"表示oc忽略大小写，"^d((?!)o)c"表示只有o忽略大小写 
		//2、Pattern.CASE_INSENSITIVE
		//解释说明:
		//^						:表示匹配开始;
		//?:https?://　　			:表示https?有,则有://;如果没有https?,则没有://,它们是一对一匹配;
		//(?:https?://)?　　		:表示捕获组0个或者1个,可以没有https:// http://;
		//[\\w]{1,}　　			:表示匹配a-zA-Z0-9,可以有多个,比如www ad 123dd等多种组合;
		//?:\\.?[\\w]{1,}　　		:表示匹配如果有.,则有[\\w]{1,};如果没有.,则没有[\\w]{1,},它们是一对一匹配;
		//(?:\\.?[\\w]{1,})+	:表示至少匹配一个.abc .com .cn;
		//[\\w-_/?&=#%:]*　　		:表配url后面的参数,包括特殊字符,可以有0个或者多个;
		//$　　					:表示匹配结束;
		//
		//量词描述一个模式吸收输入文本的方式.
		//*:前面字符或组匹配0或多个
		//+:前面字符或组匹配1或多个
		//?:前面字符或组匹配0或1个
		//{n}:前面字符或组的数量为n个
		//{n,}:前面字符或组的数量至少n个
		//{n,m}:前面字符或组数量至少n个,最多m个
	}
	
	private final static String s_str = "final static str";

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		System.out.println(s_str);
		reg() ;
		System.out.println(EnumDemo.CODE.getName());//RSA
		System.out.println(EnumDemo.CODE.getType());//1
		String regex = "\\?|\\*";
		Pattern pattern = Pattern.compile(regex);
		String[] splitStrs = pattern.split("123?123*456*456");//123 123 456 456
		System.out.println(splitStrs);
		String[] splitStrs2 = pattern.split("123?123*456*456", 2);// 123 123*456*456
		System.out.println(splitStrs2);
		
		Pattern pattern1 = Pattern.compile("\\?{2}");
		Matcher matcher = pattern1.matcher("??");
		boolean matches = matcher.matches();//true
		System.out.println(matches);
		matcher=pattern1.matcher("?");
		matches = matcher.matches();//false
		System.out.println(matches);
		
		Pattern pt = Pattern.compile("\\d+");
		Matcher mt = pt.matcher("22bb23");
		boolean match = mt.lookingAt();//true
		System.out.println(match);
		mt = pt.matcher("bb2233");
		match= mt.lookingAt();
		System.out.println(match);//false
		
		
		Data data = new Data();
		data.setscrtData("adsfad");
		data.setScrtSgn("1111adsfad");
		data.setUserIDCOM("www.v");
		data.setTeData("注解在get上");
		System.out.println(JsonMapper.toJsonString(data));
		Integer myint = 1;
		Integer newint = 1;
		Integer bint = 128;
		Integer newbint = 128;//(-128 - 127)
		System.out.println(bint == newbint);//false
		System.out.println(bint.equals(newbint));//true
		System.out.println(myint == newint);//true
		System.out.println(1 == myint);//true
		System.out.println(myint);
		System.out.println("1".equals(myint));// false
		System.out.println("1".equals(myint.toString()));// true
		System.out.println(TestDemo.class.getResource("/"));
		int count = 0 ;
		System.out.println(count);
		
		rand();
		String p = null;
		String p2 = "123";
		String p3 = "123";
		String cstr = "汉字";
		System.out.println("中文GBK占用字节："+cstr.getBytes("gbk").length); //结果是4
		System.out.println("中文UTF-8占用字节："+cstr.getBytes().length); //结果是6
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("k", 234567);// Object 为数字类型，强转(String)m.get("k")会提示java.lang.ClassCastException:
							// java.lang.Integer cannot be cast to java.lang.String,要用String.valueOf();
		m.put("k", "ab");
		Iterator<String> iter = m.keySet().iterator();
		while (iter.hasNext()) {
			System.out.println("m:" + iter.next());
		}
		long timestamp = 0L;
		// 通过Map.entrySet使用iterator遍历key和value
		Iterator<Entry<String, Object>> iterator = m.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			System.out.println(entry.getKey() + " = " + entry.getValue());
		}

		// 通过Map.entrySet遍历key和value（推荐容量大时使用）
		for (Entry<String, Object> entry : m.entrySet()) {
			System.out.println(entry.getKey() + " = " + entry.getValue());
		}
		System.out.println(System.currentTimeMillis() - timestamp);

		// 通过Map.values()遍历所有的value，但不能遍历key
		for (Object value : m.values()) {
			System.out.println(value);
		}
		String k = String.valueOf(m.get("k"));
		System.out.println(k);
		System.out.println("13800138000".substring(5, 11));
		System.out.println("hamburger".substring(4, 8)); // returns "urge"
		System.out.println("smiles".substring(1, 5));// returns "mile"
		System.out.println(System.identityHashCode(p2));// 获取对象内存地址
		System.out.println(System.identityHashCode(p3));
		System.out.println("java.io.tmpdir:" + System.getProperty("java.io.tmpdir"));
		System.out.println(String.format("测试:%s,参数2:%s", p, p2));
		System.out.println(System.getProperty("hostName"));
		System.out.println(System.getProperty("ct"));
		System.out.println(System.getProperty("user.dir"));
		String classPath = "applicationContext-test.xml";
		//
		// 加载config文件夹下的log4j.properties
		String log4jPath = System.getProperty("user.dir") + "/src/test/java/log4j.xml";
		String path = System.getProperty("user.dir") + "/src/test/java/servlet-api.jar";
		File file = new File(path);// jar包的路径
		URL url = file.toURI().toURL();
		ClassLoader loader1 = new URLClassLoader(new URL[] { url });// 创建类加载器
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();// 所有的Class对象
		Map<Class<?>, Annotation[]> classAnnotationMap = new HashMap<Class<?>, Annotation[]>();// 每个Class对象上的注释对象
		Map<Class<?>, Map<Method, Annotation[]>> classMethodAnnoMap = new HashMap<Class<?>, Map<Method, Annotation[]>>();// 每个Class对象中每个方法上的注释对象
		try {
			JarFile jarFile = new JarFile(new File(path));
			// URL url = new URL("file:" + path);
			ClassLoader loader = new URLClassLoader(new URL[] { url });// 自己定义的classLoader类，把外部路径也加到load路径里，使系统去该路经load对象
			Enumeration<JarEntry> es = jarFile.entries();
			while (es.hasMoreElements()) {
				JarEntry jarEntry = (JarEntry) es.nextElement();
				String name = jarEntry.getName();
				if (name != null && name.endsWith(".class")) {// 只解析了.class文件，没有解析里面的jar包
					// 默认去系统已经定义的路径查找对象，针对外部jar包不能用
					// Class<?> c =
					// Thread.currentThread().getContextClassLoader().loadClass(name.replace("/",".").substring(0,name.length()
					// - 6));
					Class<?> c = loader.loadClass(name.replace("/", ".").substring(0, name.length() - 6));// 自己定义的loader路径可以找到
					System.out.println(c);
					classes.add(c);
					Annotation[] classAnnos = c.getDeclaredAnnotations();
					classAnnotationMap.put(c, classAnnos);
					Method[] classMethods = c.getDeclaredMethods();
					Map<Method, Annotation[]> methodAnnoMap = new HashMap<Method, Annotation[]>();
					for (int i = 0; i < classMethods.length; i++) {
						Annotation[] a = classMethods[i].getDeclaredAnnotations();
						methodAnnoMap.put(classMethods[i], a);
					}
					classMethodAnnoMap.put(c, methodAnnoMap);
				}
			}
			System.out.println(classes.size());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// Http
		// log4jPath=System.getProperty("user.dir")+"/src/test/java/log4j.properties";
		// PropertyConfigurator.configure(log4jPath);
		DOMConfigurator.configure(TestDemo.class.getResource("/log4j.xml"));// 从class路径加载
		// Log4jConfigurer.initLogging(TestDemo.class.getResource("/log4j.xml"));
		// applicationContext = new ClassPathXmlApplicationContext(classPath);
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			private int count = 5;

			public void run() {
				if (count > 0) {
					// doWorkPerSecond();
					System.out.println("static:" + System.getProperty("ct"));
					System.out.println("construct:" + System.getProperty("st"));
					logger.info("log4j count:" + count);
					mylog.info("mylog log4j count:" + count);
					Random random = new Random();
					int r = random.nextInt(10 - 5 + 1) + 5;
					System.out.println("5-10随机：" + r);
					count--;
				} else {
					// doWorkEnd();
					cancel();
				}
			}
		};
		// timer.scheduleAtFixedRate(task, 2000L, 2000L);//task-所要安排的任务 time-首次执行任务的时间
		// period-执行一次task的时间间隔，单位毫秒
		timer.scheduleAtFixedRate(task, 0, 1000L);// task-所要安排的任务 time-首次执行任务的时间 period-执行一次task的时间间隔，单位毫秒

		final Timer timer1 = new Timer();
		// timer1.scheduleAtFixedRate(new TimerTask() {
		timer1.schedule(new TimerTask() {// 分别注释这行和上面这行试一试效果
			int count = 1;

			@Override
			public void run() {
				count++;
				if (count == 10) {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						System.out.println("延迟5s");
						e.printStackTrace();
					}
				} else {
					cancel();
				}
				SimpleDateFormat sf = new SimpleDateFormat("yyyy MM dd hh:mm:ss");
				System.out.println("当前时间：" + sf.format(System.currentTimeMillis()) + "计划时间："
						+ sf.format(scheduledExecutionTime()));
			}
		}, 1000, 1000);
		System.out.println(URLDecoder.decode("%D6%D8%C7%EC%CE%D6%B2%D6%B9%A9%D3%A6%C1%B4%B9%DC%C0%ED%D3%D0%CF%DE%B9%AB%CB%BE","gbk"));
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
		List<User> listu = null;
		System.out.println("list size:"+list.size());
		System.out.println("listu size:"+listu.size());
		List<User> newlist = new ArrayList<User>();
		User obj = new User();
		obj.setAge(12);
		System.out.println("obj getName:" + obj.getName());
		if (ObjectUtils.isEmpty(obj)) {
			System.out.println("Object Empty");
		}
		User obj1 = null;
		try {
			System.out.println(obj1.getName());
		} catch (Exception e) {
			// TODO: handle exception
			if (e instanceof NullPointerException) {
				// System.out.println("NullPointerException");
				StringBuilder sbException = new StringBuilder();
				sbException.append("NullPointerException:");
				for (StackTraceElement ele : e.getStackTrace()) {
					sbException.append(MessageFormat.format("\tat {0}.{1}({2}:{3})\n", ele.getClassName(),
							ele.getMethodName(), ele.getFileName(), ele.getLineNumber()));
					;
				}
				System.out.println(sbException);
			}
			System.out.println("obj1 exception: getLocalizedMessage=" + e.getLocalizedMessage() + " getMessage= "
					+ e.getMessage() + " getCause= " + e.getCause());
		}
		// 用于对象或数组多层校验
		if (ObjectUtils.isEmpty(obj1)) {
			System.out.println("Object is null");
		}
		// 用于数组
		if (CollectionUtils.isEmpty(list)) {
			System.out.println("Collection Empty");
		}
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
		str = "1|12|4||5";
		String[] s = str.split("\\|");
		System.out.println(s.length);
		StringTokenizer stringTokenizer = new StringTokenizer(str, "\\|");
		List<String> sl = new ArrayList<String>();
		while (stringTokenizer.hasMoreTokens()) {
			sl.add(stringTokenizer.nextToken());
		}
		for (String c : sl) {
			System.out.println("list foreach:" + c);
		}

		for (int i = 0; i < sl.size(); i++) {
			System.out.println("list for:" + sl.get(i));
		}

		Iterator<String> itreator = sl.iterator();
		while (itreator.hasNext()) {
			System.out.println("list itreator:" + itreator.next());
		}
		for (String n : s) {
			if (Long.valueOf(n).longValue() == 1L) {
				System.out.println(n);// 1
				System.out.println(Long.valueOf(n).equals(1L));// true
				System.out.println(n.equals(1));// false
				System.out.println(n.equals(String.valueOf(1L)));// true
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
		ss = "&amp;df&amp;";
		System.out.println(ss.replaceAll("&amp;", "&"));// &df&
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
		if (pos > -1) {
			StringBuilder ds = new StringBuilder("000");
			String dsd = String.valueOf(ds.charAt(pos));
			if ("0".equals(dsd)) {
				System.out.println("endpos:" + (pos + 1));
				ds.replace(pos, pos + 1, "1");
				System.out.println(ds.toString());
			}
		}

		test5(listd);
		m();
		int c = 0;
		s(c);
		Integer integer = null;
		System.out.println(integer);
		System.out.println(String.valueOf(integer));
		Long long1 = null;
		System.out.println(long1);
		System.out.println(String.valueOf(long1));
		intvalueAndValueOf();
		DemoDTO demoDTO = new DemoDTO();
		demoDTO.setData("123");
		System.out.println("-----Start demoDTO-----");
		System.out.println(demoDTO.getData());
		System.out.println(demoDTO.getInteger());
		System.out.println(demoDTO.getL());
		System.out.println(demoDTO.getBigDecimal());
		System.out.println(demoDTO.getLong1());
		System.out.println("-----End demoDTO-----");
		User u = new User();
		setData(u);
		System.out.println("u:" + u.getAge());

		// << : 左移运算符，num << 1,相当于num乘以2
		// >> : 右移运算符，num >> 1,相当于num除以2
		// >>> : 无符号右移，忽略符号位，空位都以0补齐
		int number = 10;
		// 原始数二进制
		printInfo(number);
		number = number << 1;
		// 左移一位
		printInfo(number);
		number = number >> 1;
		// 右移一位
		printInfo(number);
		
		try {
			gec();
		}catch (Exception e) {
			System.out.println(e.getCause().getMessage());//u is null
			
			System.out.println(e.getMessage());//java.lang.Exception: u is null
		}
	}
	
	public static void ec() throws Exception {
		User u = null;
		try {
			System.out.println(u.getName());
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("u is null");
		}
	}
	
	public static void gec() throws Exception {
		try {
			ec();
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e);
		}
	}

	// 方式1.一开始是这样的：
	public static void test1(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

	// 方式2.当然稍微高级一点的是这样：
	public static void test2(List<String> list) {
		for (int i = 0, lengh = list.size(); i < lengh; i++) {
			System.out.println(list.get(i));
		}
	}

	// 方式3.还有就是Iterator遍历：
	public static void test3(List<String> list) {
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	// 方式4.后来有了增强for循环：
	public static void test4(List<String> list) {
		for (String str : list) {
			System.out.println(str);
		}
	}

	// 方式5.java8以后新增的方式：
	public static void test5(List<String> list) {
		// list.forEach(System.out::println);和下面的写法等价
		list.forEach(str -> {
			System.out.println(str);
		});
		list.forEach(new Consumer<String>() {

			@Override
			public void accept(String t) {
				// TODO Auto-generated method stub
				System.out.println("====:" + t);
			}
		});
	}

	// 方式6.还有另一种：
	public static void test6(List<String> list) {
		list.iterator().forEachRemaining(str -> {
			System.out.println(str);
		});
	}

	private static void randomchar() {
		// 产生5位长度的随机字符串，中文环境下是乱码
		RandomStringUtils.random(5);

		// 使用指定的字符生成5位长度的随机字符串
		RandomStringUtils.random(5, new char[] { 'a', 'b', 'c', 'd', 'e', 'f', '1', '2', '3' });

		// 生成指定长度的字母和数字的随机组合字符串
		RandomStringUtils.randomAlphanumeric(5);

		// 生成随机数字字符串
		RandomStringUtils.randomNumeric(5);

		// 生成随机[a-z]字符串，包含大小写
		RandomStringUtils.randomAlphabetic(5);

		// 生成从ASCII 32到126组成的随机字符串
		RandomStringUtils.randomAscii(4);
	}

	/**
	 * 输出一个int的二进制数
	 * 
	 * @param num
	 */
	private static void printInfo(int num) {
		System.out.println(Integer.toBinaryString(num));
//        运行结果为：
//
//        1010
//        10100
//        1010
//        我们把上面的结果对齐一下：
//
//        43210      位数
//        --------
//         1010      十进制：10     原始数         number
//        10100      十进制：20     左移一位       number = number << 1;
//         1010      十进制：10     右移一位       number = number >> 1;
	}

	private static void setData(User u) {
		u.setAge(10);
	}

	private static void intvalueAndValueOf() {
		Double s = 2.5;
		Float k = 2.7f;
		double kk = 2.9;
		Double double1 = new Double(2.9);

		System.out.println(k.intValue());
		System.out.println(s.intValue());
//		输出结果：
//		2
//		2

//		valueOf()
//		String 类中的valueOf()：
//
//		static String valueOf(boolean b)
//		          返回 boolean 参数的字符串表示形式。
//		static String valueOf(Object obj)
//		          返回 Object 参数的字符串表示形式。
//		  表示的是将( )中的 值， 转换  成  字符串类型
//
//		eg.

		String a = "abcdefbac";

		String kkk = a.valueOf(true);

		System.out.println(kkk.getBytes());

		System.out.println(Arrays.toString(kkk.getBytes()));

//		输出结果：
//		[B@4554617c
//		[116, 114, 117, 101]
//
//		原因分析：
//		因为a.valueOf(true) 与 a 其实没有关系(String a = null ,也是可以的 )，主要是将（）中的值变成字符串类型。
//		字符串类型通过.getbytes() 转成 字符数组输出的是：字符数组的首地址的 hashcode 值
//
//		通过Arrays.toString()方法 可以输出 字符数组中的内容。
//
//		数据类型的转换方式：
//		所以有如下转换方式：

		// Double s = 2.5;
		System.out.println(s.valueOf(2.5f));
		System.out.println(s.valueOf("2"));
//		输出结果：
//		2.5
//		2.0

		// Float k = 2.7f;
		System.out.println(k.valueOf(2.9f));
		System.out.println(k.valueOf("2.533435"));
		System.out.println(k.valueOf((float) 2.5d));
//		输出结果：
//		2.9
//		2.533435
//		2.5

		Integer aa = 9;
//		System.out.println(aa.valueOf("23.0"));
//		输出结果：
//		报错Exception in thread "main" java.lang.NumberFormatException: For input string: "23.0"
//		原因：因为"23.0"  默认是double类型的，而aa类型是int。又因为"23.0"是字符串，无法强制转换
//		只能是
		System.out.println(aa.valueOf((int) 23.0));// 强转类型必须和aa一致
	}

	private static void s(int count) {
		System.out.println(count);
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
		// mlist1.removeAll(mlist2);

		List<String> mlist3 = new ArrayList<String>();
		mlist3 = mlist1;
		// mlist2.addAll(mlist1);
		// mlist2.addAll(mlist1);
		mlist2.retainAll(mlist1);// 交集
		mlist1.removeAll(mlist2);// 删除交集
		mlist2.addAll(mlist1);// 用排序后的数据添加删除交集后剩下的数据 结果应为：CBAD
		System.out.println("mlist求并集(去重):" + mlist2);
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
		int a = 128;
		int b = 129;
		System.out.println(a & b);// 128;a 的值是129，转换成二进制就是10000001，而b
									// 的值是128，转换成二进制就是10000000。根据与运算符的运算规律，只有两个位都是1，结果才是1，可以知道结果就是10000000，即128。
		System.out.println(a | b);// 129;a 的值是129，转换成二进制就是10000001，而b
									// 的值是128，转换成二进制就是10000000，根据或运算符的运算规律，只有两个位有一个是1，结果才是1，可以知道结果就是10000001，即129。
		a = 2;// 0010
		// 变量a的二进制数形式： 00000000 00000000 00000000 00000010
		// 逐位取反后，等于十进制的-3： 11111111 11111111 11111111 11111101 变成负数
		// 补码 10000000 00000000 00000000 00000010 | 00000000 00000000 00000000 00000001
		// | 100000000 00000000 00000000 00000011

		System.out.println("a 非的结果是：" + (~a)); // -3
		a = 10;// 1010
		// 00000000 00000000 00000000 00001010
		// 11111111 11111111 11111111 11110101
		// 10000000 00000000 00000000 00001010 | 00000000 00000000 00000000 00000001 |
		// 10000000 00000000 00000000 00001011

		System.out.println("a 非的结果是：" + (~a)); // -11

		a = 15;
		b = 2;
		System.out.println("a 与 b 异或的结果是：" + (a ^ b));// a 与 b 异或的结果是：13 分析上面的程序段：a 的值是15，转换成二进制为1111，而b
														// 的值是2，转换成二进制为0010，根据异或的运算规律，可以得出其结果为1101 即13。
	}

	private static void rand() {
		Random random = new Random();

		/**
		 * 生成 [m,n] 的数字 int i1 = random.nextInt() * (n-m+1)+m;
		 */
		// 生成64-128内的随机数
		int i = random.nextInt(128) % (128 - 64 + 1) + 64;
		int r = random.nextInt(128 - 64 + 1) + 64;
		System.out.println(r);
		/**
		 * 生成0-n之内的数字 int i1 = random.nextInt() * (n-0+1);
		 *
		 *
		 */
		// 生成0-64内的数字
		int i1 = random.nextInt(64) % (64 - 0 + 1);
		// System.out.println(random.nextLong());
		Long a = 2147483648L;
		Long b = 2147483648L;
		BigDecimal add = new BigDecimal(a).add(new BigDecimal(b));
		BigDecimal sub = new BigDecimal(a).subtract(new BigDecimal(b));
		System.out.println("add:" + add.longValue());
		System.out.println("sub:" + sub.longValue());
		System.out.println("生成64-128内的数字:" + String.valueOf(i));
		System.out.println("生成0-64内的数字:" + String.valueOf(i1));
	}

	/**
	 * @Author：
	 * 
	 * @Description：获取某个目录下所有直接下级文件，不包括目录下的子目录的下的文件，所以不用递归获取 @Date：
	 */
	public static List<String> getFiles(String path) {
		List<String> files = new ArrayList<String>();
		File file = new File(path);
		File[] tempList = file.listFiles();

		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				files.add(tempList[i].toString());
				// 文件名，不包含路径
				String fileName = tempList[i].getName();
			}
			if (tempList[i].isDirectory()) {
				// 这里就不递归了，
			}
		}
		return files;
	}

	private static ArrayList<String> listname = new ArrayList<String>();

	public static void readAllFile(String filepath) {
		File file = new File(filepath);
		if (!file.isDirectory()) {
			listname.add(file.getName());
		} else if (file.isDirectory()) {
			System.out.println("文件");
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(filepath);
				if (!readfile.isDirectory()) {
					listname.add(readfile.getName());
				} else if (readfile.isDirectory()) {
					readAllFile(filepath + "\\" + filelist[i]);// 递归
				}
			}
		}
		for (int i = 0; i < listname.size(); i++) {
			System.out.println(listname.get(i));
		}
	}
}
