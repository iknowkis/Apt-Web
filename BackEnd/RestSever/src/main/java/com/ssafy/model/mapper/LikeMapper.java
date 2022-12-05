package com.ssafy.model.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.model.LikeDto;

@Mapper
public interface LikeMapper {
	public int insertLike(LikeDto likeDto) throws SQLException;

	public int deleteLike(LikeDto likeDto) throws SQLException;
}
