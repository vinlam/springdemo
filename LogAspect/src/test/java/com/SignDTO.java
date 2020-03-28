package com;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SignDTO {
	@JSONField(name="Head")//fastjson
	private HeaderDTO Head;
	public HeaderDTO getHead() {
		return Head;
	}
	public void setHead(HeaderDTO head) {
		this.Head = head;
	}
	public DataDTO getData() {
		return data;
	}
	public void setData(DataDTO data) {
		this.data = data;
	}
	
	@JsonProperty("Data")//jackson
	private DataDTO data;
}
