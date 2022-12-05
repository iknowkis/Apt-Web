package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.NoticeDto;
import com.ssafy.model.NoticeParameterDto;
import com.ssafy.model.QnaDto;
import com.ssafy.model.QnaParameterDto;
import com.ssafy.util.PageNavigation;

public interface BoardService {
	public boolean writeNotice(NoticeDto noticeDto) throws Exception;
	public List<NoticeDto> listNotice(NoticeParameterDto noticeParameterDto) throws Exception;
	public PageNavigation makeNoticePageNavigation(NoticeParameterDto noticeParameterDto) throws Exception;
	
	public NoticeDto getNotice(int articleno) throws Exception;
	public void updateNoticeHit(int articleno) throws Exception;
	public boolean modifyNotice(NoticeDto noticeDto) throws Exception;
	public boolean deleteNotice(int articleno) throws Exception;
	
	public boolean writeQna(QnaDto qnaDto) throws Exception;
	public List<QnaDto> listQna(QnaParameterDto qnaParameterDto) throws Exception;
	public PageNavigation makeQnaPageNavigation(QnaParameterDto qnaParameterDto) throws Exception;
	
	public QnaDto getQna(int articleno) throws Exception;
	public void updateQnaHit(int articleno) throws Exception;
	public boolean modifyQna(QnaDto qnaDto) throws Exception;
	public boolean deleteQna(int articleno) throws Exception;
	public boolean enrollAnswer(QnaDto qnaDto) throws Exception;
}
