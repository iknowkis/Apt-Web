package com.ssafy.model.service;

import com.ssafy.model.MemberDto;

public interface MemberService {

	public int register(MemberDto memberDto) throws Exception;
	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
	public void saveRefreshToken(String userid, String refreshToken) throws Exception;
	public Object getRefreshToken(String userid) throws Exception;
	public void deleRefreshToken(String userid) throws Exception;
	public MemberDto find(MemberDto memberDto) throws Exception;
	public boolean modify(MemberDto memberDto) throws Exception;
	
}
