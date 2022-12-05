package com.ssafy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.ssafy.model.service.NaverService;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/news")
public class NaverController {
	@Autowired
	private NaverService service;
	
	@PostMapping
	public ResponseEntity<?> search(@RequestBody String keyword){
		String article = service.search(keyword);
		
		 Gson gson = new Gson();
		 
        // Json 문자열 -> Map
        Map<String, Object> map = gson.fromJson(article, Map.class);
		if (article != null) {
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<String>("fail", HttpStatus.ACCEPTED);
		}
	}
}
