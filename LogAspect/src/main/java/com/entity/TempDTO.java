package com.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(value=JsonInclude.Include.NON_EMPTY)
public class TempDTO {
	private String TempName;
	private String TempNo;
	private String TempDate;
	private String TempType;
	private String TempStatus;
	public String getTempName() {
		return TempName;
	}
	public void setTempName(String tempName) {
		TempName = tempName;
	}
	public String getTempNo() {
		return TempNo;
	}
	public void setTempNo(String tempNo) {
		TempNo = tempNo;
	}
	public String getTempDate() {
		return TempDate;
	}
	public void setTempDate(String tempDate) {
		TempDate = tempDate;
	}
	public String getTempType() {
		return TempType;
	}
	public void setTempType(String tempType) {
		TempType = tempType;
	}
	public String getTempStatus() {
		return TempStatus;
	}
	public void setTempStatus(String tempStatus) {
		TempStatus = tempStatus;
	}
}
