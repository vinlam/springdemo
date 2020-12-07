package com.entry.xml.jxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;


//@XmlType，将Java类或枚举类型映射到XML模式类型
//@XmlAccessorType(XmlAccessType.FIELD) ，控制字段或属性的序列化。FIELD表示JAXB将自动绑定Java类中的每个非静态的（static）、非瞬态的（由@XmlTransient标注）字段到XML。其他值还有XmlAccessType.PROPERTY和XmlAccessType.NONE。
//@XmlAccessorOrder，控制JAXB 绑定类中属性和字段的排序。
//@XmlJavaTypeAdapter，使用定制的适配器（即扩展抽象类XmlAdapter并覆盖marshal()和unmarshal()方法），以序列化Java类为XML。
//@XmlElementWrapper ，对于数组或集合（即包含多个元素的成员变量），生成一个包装该数组或集合的XML元素（称为包装器）。
//@XmlRootElement，将Java类或枚举类型映射到XML元素。
//@XmlElement，将Java类的一个属性映射到与属性同名的一个XML元素。
//@XmlAttribute，将Java类的一个属性映射到与属性同名的一个XML属性。
public @XmlAccessorType(value = XmlAccessType.FIELD) class Attr {

	@XmlAttribute(name = "id")
	private String id;

	@XmlAttribute(name = "secret")
	private String secret;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}
