package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.AirportWeatherForecastDto;

public interface BlendProductService {

	public List<AirportWeatherForecastDto> getAirportWeatherInfo(String date, String cc);
}
