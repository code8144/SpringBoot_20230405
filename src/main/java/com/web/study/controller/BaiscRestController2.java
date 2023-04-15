package com.web.study.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.study.dto.DataResponseDto2;
import com.web.study.dto.ResponseDto2;
import com.web.study.dto.request.BasicTestDto2;

@RestController
public class BaiscRestController2 {
	public ResponseEntity<? extends ResponseDto2 > car(BasicTestDto2 basicTestDto2){
		String carInfo = basicTestDto2.getCarname() + "(" + basicTestDto2.getNumber()+")";
		return ResponseEntity.ok().body(DataResponseDto2.of(carInfo));
	}
	@GetMapping("/car2/{number}")
	public ResponseEntity<? extends ResponseDto2> car2(@PathVariable int number){
		Map<Integer, String > carMap = new HashMap<>();
		carMap.put(1,  "sedan");
		carMap.put(2, "wegon");
		carMap.put(3, "Benz");
		carMap.put(4, "perari");
		
		return ResponseEntity.ok().body(DataResponseDto2.of(carMap.get(number)));
	}
	@PostMapping("/add")
	public ResponseEntity<? extends ResponseDto2> add(@RequestBody BasicTestDto2 basicTestDto2){

	return ResponseEntity.created(null).body(DataResponseDto2.of(basicTestDto2));
	
}
}
