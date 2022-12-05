package com.ssafy.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.model.HouseInfoDto;
import com.ssafy.model.LikeHouseDto;
import com.ssafy.model.SidoGugunCodeDto;

@Mapper
public interface HouseMapMapper {

	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	List<HouseInfoDto> getDongInGugun(String gugun) throws SQLException;
	List<HouseInfoDto> getAptInDong(String dong) throws SQLException;
	void hit(String no) throws SQLException;
	List<HouseInfoDto> getPopularApt() throws SQLException;
	List<LikeHouseDto> getLikeAptList(String userid) throws SQLException;
	
}
