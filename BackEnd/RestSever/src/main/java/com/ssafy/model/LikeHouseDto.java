package com.ssafy.model;

public class LikeHouseDto {
	private int no;
	private int aptCode;
	private String dealAmount;
	private int floor;
	private String area;
	private String aptName;
	private String userid;
	private String dongName;
	private String sidoName;
	private String gugunName;
	private String buildyear;
	
	@Override
	public String toString() {
		return "LikeHouseDto [no=" + no + ", aptCode=" + aptCode + ", dealAmount=" + dealAmount + ", floor=" + floor
				+ ", area=" + area + ", aptName=" + aptName + ", userid=" + userid + ", dongName=" + dongName
				+ ", sidoName=" + sidoName + ", gugunName=" + gugunName + ", buildyear=" + buildyear + "]";
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getAptCode() {
		return aptCode;
	}
	public void setAptCode(int aptCode) {
		this.aptCode = aptCode;
	}
	public String getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAptName() {
		return aptName;
	}
	public void setAptName(String aptName) {
		this.aptName = aptName;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getDongName() {
		return dongName;
	}
	public void setDongName(String dongName) {
		this.dongName = dongName;
	}
	public String getSidoName() {
		return sidoName;
	}
	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}
	public String getGugunName() {
		return gugunName;
	}
	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}
	public String getBuildyear() {
		return buildyear;
	}
	public void setBuildyear(String buildyear) {
		this.buildyear = buildyear;
	}
}
