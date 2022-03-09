package com.example.demo.service;

import com.example.demo.dto.WeatherDto;

public interface WeatherService {

	WeatherDto parseWeatherXml(String xml);

}
