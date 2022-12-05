package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.HouseInfoDto;
import com.ssafy.model.LikeHouseDto;
import com.ssafy.model.SidoGugunCodeDto;

public interface HouseMapService {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<HouseInfoDto> getDongInGugun(String gugun) throws Exception;
	List<HouseInfoDto> getAptInDong(String dong) throws Exception;
	List<HouseInfoDto> getPopularApt() throws Exception;
	List<LikeHouseDto> getLikeAptList(String userid) throws Exception;
	void hit(String no) throws Exception;
	
}
