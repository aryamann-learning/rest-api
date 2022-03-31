package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.demo.dto.HourlyWeatherDataDto;

public interface BlendProductService {

	public Map<String, List<HourlyWeatherDataDto>> getAirportWeatherInfo(LocalDate startDate, String cc);
}
