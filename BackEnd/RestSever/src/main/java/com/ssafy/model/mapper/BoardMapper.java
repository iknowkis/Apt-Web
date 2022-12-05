package com.ssafy.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.model.NoticeDto;
import com.ssafy.model.NoticeParameterDto;
import com.ssafy.model.QnaDto;
import com.ssafy.model.QnaParameterDto;

@Mapper
public interface BoardMapper {
	
	public int writeNotice(NoticeDto noticeDto) throws SQLException;
	public List<NoticeDto> listNotice(NoticeParameterDto noticeParameterDto) throws SQLException;
	public int getNoticeTotalCount(NoticeParameterDto noticeParameterDto) throws SQLException;
	public NoticeDto getNotice(int articleno) throws SQLException;
	public void updateNoticeHit(int articleno) throws SQLException;
	public int modifyNotice(NoticeDto noticeDto) throws SQLException;
	public void deleteNoticeMemo(int articleno) throws SQLException;
	public int deleteNotice(int articleno) throws SQLException;
	
	public int writeQna(QnaDto qnaDto) throws SQLException;
	public List<QnaDto> listQna(QnaParameterDto qnaParameterDto) throws SQLException;
	public int getQnaTotalCount(QnaParameterDto qnaParameterDto) throws SQLException;
	public QnaDto getQna(int articleno) throws SQLException;
	public void updateQnaHit(int articleno) throws SQLException;
	public int modifyQna(QnaDto qnaDto) throws SQLException;
	public void deleteQnaMemo(int articleno) throws SQLException;
	public int deleteQna(int articleno) throws SQLException;
	public int enrollAnswer(QnaDto qnaDto) throws SQLException;
}