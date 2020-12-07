package com.entry.xml;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ElementConverter implements Converter {

    @Override
    public boolean canConvert(Class type) {
        return type.equals(XmlElment.class);
    }
    /**
     * 将java对象转为xml时使用
     */
    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        XmlElment elment = (XmlElment) source;
        writer.addAttribute("attribute", elment.getAttribute());
        writer.setValue(elment.getText());
    }
    /**
     * 将xml转为java对象使用
     */
    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        // 在解析order元素时，先创建一个Order对象
        XmlElment element = new XmlElment();
        // 将attribute元素定义为标签属性
        element.setAttribute(reader.getAttribute("attribute"));
        // 将text定义为标签值
        element.setText(reader.getValue());
        return element;
    }
}
