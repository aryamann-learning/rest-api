package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.WeatherDto;
import com.example.demo.service.WeatherService;

@RestController
@RequestMapping("/weather-service")
public class WeatherController {

	@Autowired
	WeatherService weatherService;

	@GetMapping("/weather/info")
	public ResponseEntity<WeatherDto> getWeatherInfo() {
		return new ResponseEntity<>(weatherService.parseWeatherXml(), HttpStatus.OK);
	}
}
