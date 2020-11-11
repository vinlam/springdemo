package com;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
//JsonAutoDetect.Visibility.ANY : 表示所有字段都可以被发现, 包括private修饰的字段, 解决大小写问题
//JsonAutoDetect.Visibility.NONE : 表示get方法不可见,解决字段重复问题

public class Data {
	@JsonProperty("data")
    //private String ScrtData;//如S开头是写则数据会重复：{"scrtData":"adsfad","scrtSgn":"1111adsfad","data":"adsfad","sign":"1111adsfad"}
	private String scrtData;//{"scrtSgn":"1111adsfad","data":"adsfad","sign":"1111adsfad"}
	@JsonProperty("sign")
	private String ScrtSgn;
	@JsonProperty("USER_ID_COM")
	private String userIDCOM;
	//@JsonProperty("TData")
	private String TeData;//当私有变量开头为大写时，在变量上@JsonProperty("TData")会出现字段重复{"scrtSgn":"1111adsfad","teData":"注解在get上","data":"adsfad","sign":"1111adsfad","USER_ID_COM":"www.v","TData":"注解在get上"}

	public String getUserIDCOM() {
		return userIDCOM;
	}
	public void setUserIDCOM(String userIDCOM) {
		this.userIDCOM = userIDCOM;
	}
	public String getscrtData() {
		return scrtData;
	}
	public void setscrtData(String scrtData) {
		this.scrtData = scrtData;
	}
	public String getScrtSgn() {
		return ScrtSgn;
	}
	public void setScrtSgn(String scrtSgn) {
		ScrtSgn = scrtSgn;
	}
	@JsonProperty("TData")//{"scrtSgn":"1111adsfad","data":"adsfad","sign":"1111adsfad","USER_ID_COM":"www.v","TData":"注解在get上"}
	public String getTeData() {
		return TeData;
	}
	public void setTeData(String teData) {
		TeData = teData;
	}
}
