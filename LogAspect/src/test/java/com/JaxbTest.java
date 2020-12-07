package com;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import com.entry.xml.jxb.Attr;
import com.entry.xml.jxb.User;
/**
 * @author <a href="">yida</a>
 * @Version 2020-04-23 16:11
 * @Version 1.0
 * @Description JaxbTest
 */
public class JaxbTest {
	public static void main(String[] args) {
		try {
//			@XmlType，将Java类或枚举类型映射到XML模式类型
//			@XmlAccessorType(XmlAccessType.FIELD) ，控制字段或属性的序列化。FIELD表示JAXB将自动绑定Java类中的每个非静态的（static）、非瞬态的（由@XmlTransient标注）字段到XML。其他值还有XmlAccessType.PROPERTY和XmlAccessType.NONE。
//			@XmlAccessorOrder，控制JAXB 绑定类中属性和字段的排序。
//			@XmlJavaTypeAdapter，使用定制的适配器（即扩展抽象类XmlAdapter并覆盖marshal()和unmarshal()方法），以序列化Java类为XML。
//			@XmlElementWrapper ，对于数组或集合（即包含多个元素的成员变量），生成一个包装该数组或集合的XML元素（称为包装器）。
//			@XmlRootElement，将Java类或枚举类型映射到XML元素。
//			@XmlElement，将Java类的一个属性映射到与属性同名的一个XML元素。
//			@XmlAttribute，将Java类的一个属性映射到与属性同名的一个XML属性。
			// 创建JAXBContext对象，注意一定要传@XmlRootElement的所标记的类的类型
			JAXBContext jaxbContext = JAXBContext.newInstance(User.class, Attr.class);
			// 拿到xml解析对象
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
					+ "<user xmls=\"http://s3.amazonaws.com/doc/2006-03-01/\">\n"
					+ "    <password id=\"333\" secret=\"@#$\"/>\n" + "    <name>nicole</name>\n"
					+ "    <age>18</age>\n" + "</user>";

			// 解析为bean
			User bean = (User) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
			System.out.println(bean.toString());

			// 将bean解析为xml字符串
			Marshaller marshaller = jaxbContext.createMarshaller();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			marshaller.marshal(bean, byteArrayOutputStream);
			byteArrayOutputStream.flush();
			byteArrayOutputStream.close();
			String s = new String(byteArrayOutputStream.toByteArray(), "utf-8");
			System.out.println(s);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
