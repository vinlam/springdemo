package com.entry.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/* XStream 注解 */
@XStreamAlias("Person")//定义根节点别名
public class Person {

    public Person(String id, XmlElment name, String sex, int age, Address address) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.address = address;
	}

	@XStreamOmitField //忽略该属性
    private String id;

    @XStreamAlias("_Name")//定义属性别名
    private XmlElment name;//XmlElment实体主要是为了方便在该标签上添加属性

    @XStreamAlias("_Sex")//定义属性别名
    private String sex;

    @XStreamAlias("_Age")
    private int age;

    @XStreamAlias("_Address")
    private Address address;
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public XmlElment getName() {
		return name;
	}

	public void setName(XmlElment name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String toString() {
    	return "Person{" + "id='" + id + '\'' + ", age='" + age + '\'' + ", sex=" + sex + ", address=" + address.toString() + '}'; 
    }
}

