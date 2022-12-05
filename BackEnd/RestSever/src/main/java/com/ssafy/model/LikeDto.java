package com.ssafy.model;

public class LikeDto {
	private int likeno;
	private String no;
	private String userid;
	public LikeDto(int likeno, String no, String userid) {
		this.likeno = likeno;
		this.no = no;
		this.userid = userid;
	}
	public LikeDto() {}
	public int getLikeno() {
		return likeno;
	}
	public void setLikeno(int likeno) {
		this.likeno = likeno;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}
