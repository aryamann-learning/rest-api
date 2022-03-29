package com.example.demo.dao;

import com.example.demo.dto.HourlyWeatherDataDto;

public interface AirportweatherDao {

	public void saveAirportWeatherData(String airport,int date, HourlyWeatherDataDto dto);

}