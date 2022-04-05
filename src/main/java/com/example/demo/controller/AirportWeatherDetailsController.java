package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.HourlyWeatherDataDto;
import com.example.demo.service.AirportWeatherDetailsService;

@RestController
@RequestMapping("airport-weather-service")
public class AirportWeatherDetailsController {

	@Autowired
	AirportWeatherDetailsService airportWeatherDetailsService;

	@GetMapping("/airport/{date}/{cc}/info")
	public Map<String, List<HourlyWeatherDataDto>> getAirportWeatherInfo(
			@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@PathVariable("cc") String cc) {
		return airportWeatherDetailsService.getAirportWeatherInfo(startDate, cc);
	}

}
