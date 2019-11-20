package com;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.util.DateJsonDeserializer;
import com.util.DateJsonSerializer;
import com.util.JsonMapper;

public class DateSerializerTest {

//	@JsonDeserialize(using= DateJsonDeserializer.class)
//	@JsonSerialize(using= DateJsonSerializer.class)
	@JsonFormat(pattern = "YYYY/MM/dd",timezone = "GMT+8")
	private static Date time;
	
    public static void main(String[] args) {
		time = new Date();
		System.out.println("当前时间:");
        System.out.println(JsonMapper.toJsonString(time));
        System.out.println();
        DataEntity dataEntity = new DataEntity();
        dataEntity.setDate(time);
        System.out.println(dataEntity.getDate());
        System.out.println(JsonMapper.toJsonString(dataEntity));
        // 从1970年1月1日 早上8点0分0秒 开始经历的毫秒数
        Date d2 = new Date(5000);
        System.out.println("从1970年1月1日 早上8点0分0秒 开始经历了5秒的时间");
        System.out.println(d2);
        
		
	}
}
