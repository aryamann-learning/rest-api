package com.example.demo.dao;

import java.util.LinkedHashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.HourlyWeatherDataDto;

@Repository
public class AirportWeatherDaoImpl implements AirportweatherDao {

	@Autowired
	private DataSource dataSource;

	@Override
	public void saveAirportWeatherData(String airport, int date, HourlyWeatherDataDto dto) {
		String query = "insert into airport_weather_details (airport,apt_date,apt_hour,apt_tmp,apt_wdr,apt_wsp,apt_gst,apt_p01,apt_cig,apt_vis) "
				+ "values (:airport,:date,:hour,:tmp,:wdr,:wsp,:gst,:p01,:cig,:vis)";
		LinkedHashMap<String, Object> namedParameters = new LinkedHashMap<>();
		namedParameters.put("airport", airport);
		namedParameters.put("date", date);
		namedParameters.put("hour", dto.getHourlyForecast());
		namedParameters.put("tmp", dto.getTemperature());
		namedParameters.put("wdr", dto.getWindDirection());
		namedParameters.put("wsp", dto.getWindSpeed());
		namedParameters.put("gst", dto.getWindGust());
		namedParameters.put("p01", dto.getPrecipChance());
		namedParameters.put("cig", dto.getCeilingHeight());
		namedParameters.put("vis", dto.getVisibility());
		NamedParameterJdbcTemplate namedParamterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		namedParamterJdbcTemplate.update(query, namedParameters);
	}
}
