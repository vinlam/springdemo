package com.entry.xml;

import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

/* XStream 注解 */
@XStreamConverter(value= ToAttributedValueConverter.class, strings={"text"})//XStream自带转换器，会将“text”之外的属性定义为节点的属性，text为该节点的值
//在遇见XML标签既有既有属性，又有值的情况。如 <_Name attribute="中文名">汤姆</_Name>。
//此处可以使用XmlElment实体配合@XStreamConverter(value= ToAttributedValueConverter.class, strings={"text"})注解实现，也可以通过自定义转换器实现
//@XStreamConverter(ElementConverter.class)//自定义转换器，将text之外的值定义为XML标签属性
public class XmlElment{

    /* 属性 */
    private String attribute;

    /* 内容 */
    private String text;

	public XmlElment(String attribute, String text) {
		this.attribute = attribute;
		this.text = text;
	}

	public XmlElment() {
		// TODO Auto-generated constructor stub
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
