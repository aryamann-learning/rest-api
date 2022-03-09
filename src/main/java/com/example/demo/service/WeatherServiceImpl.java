package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.WeatherDto;

@Service
public class WeatherServiceImpl implements WeatherService {

	@Override
	public WeatherDto parseWeatherXml(String xml) {
		WeatherDto weatherDto = new WeatherDto();
		String commandResult = xml.substring(xml.indexOf("CDATA") + 6, xml.indexOf("End Of Text"));
		String weatherData = commandResult.substring(commandResult.lastIndexOf("-") + 2);
		String[] values = weatherData.split(" ");
		weatherDto.setTime(values[0]);
		weatherDto.setStn(values[1]);
		weatherDto.setTemp(Double.parseDouble(values[2]));
		weatherDto.setDewpt(Double.parseDouble(values[3]));
		weatherDto.setWind(values[4]);
		weatherDto.setVsby(Double.parseDouble(values[5]));
		weatherDto.setSky(String.format("%s %s %s", values[6], values[7], values[8]));
		weatherDto.setWeather(values[9]);
		weatherDto.setQueryTime(
				String.format("%s %s %s %s %s", values[12], values[13], values[14], values[15], values[16]));

		return weatherDto;
	}
}
