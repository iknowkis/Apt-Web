package com.ssafy.model.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.LikeDto;
import com.ssafy.model.NoticeDto;
import com.ssafy.model.mapper.LikeMapper;

@Service
public class LikeService {
	
	@Autowired
	private SqlSession sqlSession;
	
	public boolean insertLike(LikeDto likeDto) throws Exception{
		if (sqlSession.getMapper(LikeMapper.class).insertLike(likeDto) == 1) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean deleteLike(LikeDto likeDto) throws Exception {
		if (sqlSession.getMapper(LikeMapper.class).deleteLike(likeDto) == 1) {
			return true;
		}
		else {
			return false;
		}
	}
}
