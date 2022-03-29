package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.dto.HourlyWeatherDataDto;

public interface BlendProductService {

	public Map<String, List<HourlyWeatherDataDto>> getAirportWeatherInfo(String date, String cc);
}
