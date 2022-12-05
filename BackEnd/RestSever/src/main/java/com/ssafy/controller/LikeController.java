package com.ssafy.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.model.HouseInfoDto;
import com.ssafy.model.LikeDto;
import com.ssafy.model.LikeHouseDto;
import com.ssafy.model.service.HouseMapService;
import com.ssafy.model.service.LikeService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/like")
@Api("Like 컨트롤러  API V1")
public class LikeController {

	@Autowired
	private LikeService likeService;
	
	@Autowired
	private HouseMapService houseService;

	@PostMapping
	public ResponseEntity<?> like(@RequestBody LikeDto likeDto) throws Exception {
		HttpStatus status = HttpStatus.ACCEPTED;
		String msg = "success";
		try {
			if (!likeService.insertLike(likeDto)) {
				msg = "no";
			}
		} catch (Exception e) {
			msg = "error occured";
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<String>(msg, status);
	}
	@GetMapping("/popular")
	public ResponseEntity<List<HouseInfoDto>> apt() throws Exception {
		return new ResponseEntity<List<HouseInfoDto>>(houseService.getPopularApt(), HttpStatus.OK);
	}
	@PostMapping("/list")
	public ResponseEntity<List<LikeHouseDto>> getList(@RequestBody Object userid) throws Exception {
		return new ResponseEntity<List<LikeHouseDto>>(houseService.getLikeAptList((String) userid), HttpStatus.OK);
	}
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestBody LikeDto likeDto) throws Exception {
		System.out.println(likeDto);
		HttpStatus status = HttpStatus.ACCEPTED;
		String msg = "success";
		try {
			if (!likeService.deleteLike(likeDto)) {
				msg = "no";
			}
		} catch (Exception e) {
			msg = "error occured";
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<String>(msg, status);
	}
}
