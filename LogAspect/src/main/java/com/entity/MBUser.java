package com.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//import com.mysql.jdbc.Blob;
@Entity
@Table(name = "userinf_base")
public class MBUser implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String cstno        = null;
	private String logonid      = null;
	private String password     = null;
	private String name         = null;
	private String sex          = null;
	private String ctftype      = null;
	private String ctfno        = null;
	private String mobile       = null;
	private String registtime   = null;
	private String salerno      = null;
	private String storeno      = null;
	private String birthday     = null;
	private String email        = null;
	private String orgid        = null;
	private String phone        = null;
	private String address      = null;
	private String zipcode      = null;
	private String channel      = null;
//	@Column(name="signdata",columnDefinition="BLOB")
//    private byte[] signdata     = null;
//	public byte[] getSigndata() {
//		return signdata;
//	}
//	public void setSigndata(byte[] signdata) {
//		this.signdata = signdata;
//	}
//	public byte[] getLegacydatavarchar() {
//		return legacydatavarchar;
//	}
//	public void setLegacydatavarchar(byte[] legacydatavarchar) {
//		this.legacydatavarchar = legacydatavarchar;
//	}
//	public byte[] getLegacysigndatablob() {
//		return legacysigndatablob;
//	}
//	public void setLegacysigndatablob(byte[] legacysigndatablob) {
//		this.legacysigndatablob = legacysigndatablob;
//	}
//	public byte[] getHairremovedatavarchar() {
//		return hairremovedatavarchar;
//	}
//	public void setHairremovedatavarchar(byte[] hairremovedatavarchar) {
//		this.hairremovedatavarchar = hairremovedatavarchar;
//	}
//	public byte[] getHairremovesigndata() {
//		return hairremovesigndata;
//	}
//	public void setHairremovesigndata(byte[] hairremovesigndata) {
//		this.hairremovesigndata = hairremovesigndata;
//	}
//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}
//	@Column(name="legacydatavarchar",columnDefinition="BLOB")
//    private byte[] legacydatavarchar = null;
//	@Column(name="legacysigndatablob",columnDefinition="BLOB")
//    private byte[] legacysigndatablob = null;
//	@Column(name="hairremovedatavarchar",columnDefinition="BLOB")
//    private byte[] hairremovedatavarchar = null;
//	@Column(name="hairremovesigndata",columnDefinition="BLOB")
//    private byte[] hairremovesigndata = null;
	@Id
	@Column(name="cstno",unique = true, nullable = false, precision = 19, scale = 0)
	public String getCstno() {
		return cstno;
	}
	public void setCstno(String cstno) {
		this.cstno = cstno;
	}
	
	@Column(name="logonid")
	public String getLogonid() {
		return logonid;
	}
	public void setLogonid(String logonid) {
		this.logonid = logonid;
	}
	
	@Column(name="password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="sex")
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Column(name="ctftype")
	public String getCtftype() {
		return ctftype;
	}
	public void setCtftype(String ctftype) {
		this.ctftype = ctftype;
	}
	
	@Column(name="ctfno")
	public String getCtfno() {
		return ctfno;
	}
	public void setCtfno(String ctfno) {
		this.ctfno = ctfno;
	}
	
	@Column(name="mobile")
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(name="registtime")
	public String getRegisttime() {
		return registtime;
	}
	public void setRegisttime(String registtime) {
		this.registtime = registtime;
	}
	
	@Column(name="salerno")
	public String getSalerno() {
		return salerno;
	}
	public void setSalerno(String salerno) {
		this.salerno = salerno;
	}
	
	@Column(name="storeno")
	public String getStoreno() {
		return storeno;
	}
	public void setStoreno(String storeno) {
		this.storeno = storeno;
	}
	
	@Column(name="birthday")
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	@Column(name="email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name="orgid")
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	
	@Column(name="phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name="address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name="zipcode")
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	@Column(name="channel")
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
}
