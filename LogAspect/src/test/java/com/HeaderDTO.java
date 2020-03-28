package com;

public class HeaderDTO {
	private String Rsp_Tm;
	private String Rsp_Dt;
	private String Rsp_Jrnl_No;
	private String Sys_Evt_Trace_Id;
	private String Txn_Rsp_cd_Dsc;
	private String Txn_Rsp_Inf;
	
	public String getRsp_Tm() {
		return Rsp_Tm;
	}
	public void setRsp_Tm(String rsp_Tm) {
		Rsp_Tm = rsp_Tm;
	}
	public String getRsp_Dt() {
		return Rsp_Dt;
	}
	public void setRsp_Dt(String rsp_Dt) {
		Rsp_Dt = rsp_Dt;
	}
	public String getRsp_Jrnl_No() {
		return Rsp_Jrnl_No;
	}
	public void setRsp_Jrnl_No(String rsp_Jrnl_No) {
		Rsp_Jrnl_No = rsp_Jrnl_No;
	}
	public String getSys_Evt_Trace_Id() {
		return Sys_Evt_Trace_Id;
	}
	public void setSys_Evt_Trace_Id(String sys_Evt_Trace_Id) {
		Sys_Evt_Trace_Id = sys_Evt_Trace_Id;
	}
	public String getTxn_Rsp_cd_Dsc() {
		return Txn_Rsp_cd_Dsc;
	}
	public void setTxn_Rsp_cd_Dsc(String txn_Rsp_cd_Dsc) {
		Txn_Rsp_cd_Dsc = txn_Rsp_cd_Dsc;
	}
	public String getTxn_Rsp_Inf() {
		return Txn_Rsp_Inf;
	}
	public void setTxn_Rsp_Inf(String txn_Rsp_Inf) {
		Txn_Rsp_Inf = txn_Rsp_Inf;
	}
}
