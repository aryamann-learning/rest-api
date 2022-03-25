package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AirportWeatherForecastDto;
import com.example.demo.service.BlendProductService;

@RestController
@RequestMapping("/blend-service")
public class BlendProductController {

	@Autowired
	BlendProductService blendProductService;

	@GetMapping("/airport/{date}/{cc}/info")
	public List<AirportWeatherForecastDto> getAirportWeatherInfo(@PathVariable("date") String date,
			@PathVariable("cc") String cc) {
		return blendProductService.getAirportWeatherInfo(date, cc);
	}
}
