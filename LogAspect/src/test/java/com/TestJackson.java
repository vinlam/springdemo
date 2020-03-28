package com;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Before;
import org.junit.Test;

import com.entity.PersonXml;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.thoughtworks.xstream.XStream;


/**
 * This class was used to test jackson converter, jackson was used to convert json or xml to javabean or convert javabean to json or xml.
 * @author hualei
 * @date Apr 28, 2017 4:14:53 PM
 * 
 */
public class TestJackson {

    /**
     * convert json to javabean or javabean to json.
     */
    private ObjectMapper objectMapper = new ObjectMapper();
    /**
     * convert xml to javabean or javabean to xml.
     */
    private XmlMapper xmlMapper = new XmlMapper();

    private PersonXml person;

    @Before
    public void init(){
        person = new PersonXml();
        person.setName("sarah");
        person.setAge(10);
        person.setSex("male");
        person.setTelephone("12345678");
        List<String> friends = new ArrayList<String>();
        friends.add("mattew");
        friends.add("phoenix");
//      String[] friends = new String[2];
//      friends[0] = "mattew";
//      friends[1] = "phoenix";
        person.setFriends(friends);
    }

    /**
     * convert java bean to json.
     * @throws IOException 
     */
    @Test
    public void testJavabeanToJSON() throws IOException{
        // java bean to json
        String json = objectMapper.writeValueAsString(person);
        System.out.println("json:" + json);
        //json:{"name":"sarah","age":10,"sex":"male","telephone":"12345678","friends":["mattew","phoenix"]}

        //JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
        //jsonGenerator.writeObject(person);
    }

    /**
     * convert json to java bean.
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @Test
    public void testJsonToJavabean() throws JsonParseException, JsonMappingException, IOException{
        String json = "{\"name\":\"sarah\",\"age\":10,\"sex\":\"male\",\"telephone\":\"12345678\",\"friends\":[\"mattew\",\"phoenix\"]}";
        // json to java bean
        PersonXml person = objectMapper.readValue(json, PersonXml.class);
        System.out.println("person:" + person);
        // person:Person [name=sarah, age=10, sex=male, telephone=12345678, friends={mattew, phoenix}]
    }

    /**
     * convert xml to javabean.
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @Test
    public void testXmlToJavabean() throws JsonParseException, JsonMappingException, IOException{
        XStream xstream = new XStream();
        xstream.alias("person", PersonXml.class);
        String xml = xstream.toXML(person);
        System.out.println("xml:" + xml);
        // xml: <person><name>sarah</name><age>10</age><sex>male</sex><telephone>12345678</telephone><friends><string>mattew</string><string>phoenix</string></friends></person>

        // xml to java bean
        PersonXml generatePerson = xmlMapper.readValue(xml, PersonXml.class);
        System.out.println("person:" + generatePerson.toString());
        // person:Person [name=sarah, age=10, sex=male, telephone=12345678, friends={mattew, phoenix}]
    }

    /**
     * convert javabean to xml.
     * @throws IOException
     */
    @Test
    public void testJavabeanToXml() throws IOException{
        // java bean to xml
        String xml = xmlMapper.writeValueAsString(person);
        System.out.println("xml:" + xml);
        // xml:<person xmlns=""><name>sarah</name><age>10</age><sex>male</sex><telephone>12345678</telephone><friends><string>mattew</string><string>phoenix</string></friends></person>

        //String xml = xmlMapper.writer().with(SerializationFeature.WRAP_ROOT_VALUE).withRootName("person").writeValueAsString(person);
        //System.out.println("xml:" + xml);
    }

    /**
     * convert map to json.
     * @throws JsonProcessingException
     */
    @Test
    public void testMapToJSON() throws JsonProcessingException{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "sarah");
        map.put("age", 10);

        // map to json
        String json = objectMapper.writeValueAsString(map);
        System.out.println("json:" + json);
        // json:{"friends":["mattew","phoenix"],"age":10,"name":"sarah"}
    }

    /**
     * convert json to map.
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testJsonToMap() throws JsonParseException, JsonMappingException, IOException{
        String json = "{\"friends\":[\"mattew\",\"phoenix\"],\"age\":10,\"name\":\"sarah\"}";
        // json to map 
        Map map = objectMapper.readValue(json, HashMap.class);
        Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Entry<String, Object> entry = (Entry<String, Object>)iterator.next();
            System.out.print(entry.getKey() + ":" + entry.getValue() + ", ");
            // friends:[mattew, phoenix], name:sarah, age:10, 
        }
    }

}
