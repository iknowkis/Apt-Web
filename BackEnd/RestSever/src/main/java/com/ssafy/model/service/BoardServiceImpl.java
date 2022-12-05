package com.ssafy.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.model.NoticeDto;
import com.ssafy.model.NoticeParameterDto;
import com.ssafy.model.QnaDto;
import com.ssafy.model.QnaParameterDto;
import com.ssafy.model.mapper.BoardMapper;
import com.ssafy.util.PageNavigation;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean writeNotice(NoticeDto noticeDto) throws Exception {
		if(noticeDto.getSubject() == null || noticeDto.getContent() == null) {
			throw new Exception();
		}
		return sqlSession.getMapper(BoardMapper.class).writeNotice(noticeDto) == 1;
	}

	@Override
	public List<NoticeDto> listNotice(NoticeParameterDto noticeParameterDto) throws Exception {
		int start = noticeParameterDto.getPg() == 0 ? 0 : (noticeParameterDto.getPg() - 1) * noticeParameterDto.getSpp();
		noticeParameterDto.setStart(start);
		return sqlSession.getMapper(BoardMapper.class).listNotice(noticeParameterDto);
	}

	@Override
	public PageNavigation makeNoticePageNavigation(NoticeParameterDto noticeParameterDto) throws Exception {
		int naviSize = 5;
		PageNavigation pageNavigation = new PageNavigation();
		pageNavigation.setCurrentPage(noticeParameterDto.getPg());
		pageNavigation.setNaviSize(naviSize);
		int totalCount = sqlSession.getMapper(BoardMapper.class).getNoticeTotalCount(noticeParameterDto);//총글갯수  269
		pageNavigation.setTotalCount(totalCount);  
		int totalPageCount = (totalCount - 1) / noticeParameterDto.getSpp() + 1;//27
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = noticeParameterDto.getPg() <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < noticeParameterDto.getPg();
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
	}

	@Override
	public NoticeDto getNotice(int articleno) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).getNotice(articleno);
	}
	
	@Override
	public void updateNoticeHit(int articleno) throws Exception {
		sqlSession.getMapper(BoardMapper.class).updateNoticeHit(articleno);
	}

	@Override
	@Transactional
	public boolean modifyNotice(NoticeDto noticeDto) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).modifyNotice(noticeDto) == 1;
	}

	@Override
	@Transactional
	public boolean deleteNotice(int articleno) throws Exception {
		sqlSession.getMapper(BoardMapper.class).deleteNoticeMemo(articleno);
		return sqlSession.getMapper(BoardMapper.class).deleteNotice(articleno) == 1;
	}
//	aaa
	@Override
	public boolean writeQna(QnaDto qnaDto) throws Exception {
		if(qnaDto.getSubject() == null || qnaDto.getContent() == null) {
			throw new Exception();
		}
		return sqlSession.getMapper(BoardMapper.class).writeQna(qnaDto) == 1;
	}

	@Override
	public List<QnaDto> listQna(QnaParameterDto qnaParameterDto) throws Exception {
		int start = qnaParameterDto.getPg() == 0 ? 0 : (qnaParameterDto.getPg() - 1) * qnaParameterDto.getSpp();
		qnaParameterDto.setStart(start);
		return sqlSession.getMapper(BoardMapper.class).listQna(qnaParameterDto);
	}

	@Override
	public PageNavigation makeQnaPageNavigation(QnaParameterDto qnaParameterDto) throws Exception {
		int naviSize = 5;
		PageNavigation pageNavigation = new PageNavigation();
		pageNavigation.setCurrentPage(qnaParameterDto.getPg());
		pageNavigation.setNaviSize(naviSize);
		int totalCount = sqlSession.getMapper(BoardMapper.class).getQnaTotalCount(qnaParameterDto);//총글갯수  269
		pageNavigation.setTotalCount(totalCount);  
		int totalPageCount = (totalCount - 1) / qnaParameterDto.getSpp() + 1;//27
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = qnaParameterDto.getPg() <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < qnaParameterDto.getPg();
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
	}

	@Override
	public QnaDto getQna(int articleno) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).getQna(articleno);
	}
	
	@Override
	public void updateQnaHit(int articleno) throws Exception {
		sqlSession.getMapper(BoardMapper.class).updateQnaHit(articleno);
	}

	@Override
	@Transactional
	public boolean modifyQna(QnaDto qnaDto) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).modifyQna(qnaDto) == 1;
	}

	@Override
	@Transactional
	public boolean deleteQna(int articleno) throws Exception {
		sqlSession.getMapper(BoardMapper.class).deleteQnaMemo(articleno);
		return sqlSession.getMapper(BoardMapper.class).deleteQna(articleno) == 1;
	}

	@Override
	public boolean enrollAnswer(QnaDto qnaDto) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).enrollAnswer(qnaDto) == 1;
	}
}