package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class JacksonStreamApi {
	public static void main(String[] args) throws IOException {
        JsonFactory factory = new JsonFactory();
        String s = "{\"id\": 1,\"name\": \"小明\",\"array\": [\"1\", \"2\"]," +
                "\"test\":\"I'm test\",\"nullNode\":null,\"base\": {\"major\": \"物联网\",\"class\": \"3\"}}";

        //这里就举一个比较简单的例子，Generator的用法就是一个一个write即可。
        File file = new File("/json.txt");
        JsonGenerator jsonGenerator = factory.createGenerator(file, JsonEncoding.UTF8);
        //对象开始
        jsonGenerator.writeStartObject();
        //写入一个键值对
        jsonGenerator.writeStringField("name", "小光");
        //对象结束
        jsonGenerator.writeEndObject();
        //关闭jsonGenerator
        jsonGenerator.close();
        //读取刚刚写入的json
        FileInputStream inputStream = new FileInputStream(file);
        int i = 0;
        final int SIZE = 1024;
        byte[] buf = new byte[SIZE];
        StringBuilder sb = new StringBuilder();
        while ((i = inputStream.read(buf)) != -1) {
            System.out.println(new String(buf,0,i));
        }
        inputStream.close();


        //JsonParser解析的时候，思路是把json字符串根据边界符分割为若干个JsonToken，这个JsonToken是一个枚举类型。
        //下面这个小例子，可以看出JsonToken是如何划分类型的。
        JsonParser parser = factory.createParser(s);
        while (!parser.isClosed()){
            JsonToken token = parser.currentToken();
            System.out.println(token);
            parser.nextToken();
        }

        JsonParser jsonParser = factory.createParser(s);
        //下面是一个解析的实例
        while (!jsonParser.isClosed()) {
            JsonToken token  = jsonParser.nextToken();
            if (JsonToken.FIELD_NAME.equals(token)) {
                String currentName = jsonParser.currentName();
                token = jsonParser.nextToken();
                if ("id".equals(currentName)) {
                    System.out.println("id:" + jsonParser.getValueAsInt());
                } else if ("name".equals(currentName)) {
                    System.out.println("name:" + jsonParser.getValueAsString());
                } else if ("array".equals(currentName)) {
                    token = jsonParser.nextToken();
                    while (!JsonToken.END_ARRAY.equals(token)) {
                        System.out.println("array:" + jsonParser.getValueAsString());
                        token = jsonParser.nextToken();
                    }
                }
            }
        }
    }
}
