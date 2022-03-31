package com.example.demo.dao;

import java.time.LocalDate;

import com.example.demo.dto.HourlyWeatherDataDto;

public interface AirportweatherDao {

	public void saveAirportWeatherData(String airport,LocalDate date, HourlyWeatherDataDto dto);

}