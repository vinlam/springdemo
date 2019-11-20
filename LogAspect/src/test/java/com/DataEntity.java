package com;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.util.DateJsonDeserializer;
import com.util.DateJsonSerializer;

public class DataEntity{
	@JsonDeserialize(using= DateJsonDeserializer.class)
	@JsonSerialize(using= DateJsonSerializer.class)
	//@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss",timezone =  "GMT+8")
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
