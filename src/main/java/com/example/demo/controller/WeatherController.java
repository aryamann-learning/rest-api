package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.WeatherDto;
import com.example.demo.service.WeatherService;

@RestController
@RequestMapping("/weather-service")
public class WeatherController {

	@Autowired
	WeatherService weatherService;

	@PostMapping("/weather/info")
	public ResponseEntity<WeatherDto> getWeatherInfo(@RequestBody String xml) {
		return new ResponseEntity<>(weatherService.parseWeatherXml(xml), HttpStatus.OK);
	}
}
