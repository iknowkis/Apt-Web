package com.ssafy.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.model.MemberDto;

@Mapper
public interface MemberMapper {
	public int register(MemberDto memberDto) throws SQLException;
	public MemberDto login(MemberDto memberDto) throws SQLException;
	public MemberDto userInfo(String userid) throws SQLException;
	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	public Object getRefreshToken(String userid) throws SQLException;
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;
	public MemberDto find(MemberDto memberDto) throws SQLException;
	public int modify(MemberDto memberDto) throws SQLException;
}
