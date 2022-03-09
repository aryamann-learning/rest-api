package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.WeatherDto;

@Service
public class WeatherServiceImpl implements WeatherService {

	@Override
	public WeatherDto parseWeatherXml(String xml) {
		if (xml.trim().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request can't be empty");
		}

		WeatherDto weatherDto = new WeatherDto();
		String cData = xml.substring(xml.indexOf("CDATA"), xml.indexOf("End Of Text"));
		String patternData = cData.substring(cData.indexOf("-"), cData.lastIndexOf("-")).trim();
		String data = cData.substring(cData.lastIndexOf("-") + 1, cData.lastIndexOf("-") + patternData.length()).trim();
		String[] patterns = patternData.split("- ");
		int startIndex = 0;
		ArrayList<String> result = new ArrayList<>();
		for (String pattern : patterns) {
			int endIndex = startIndex + pattern.length() + 2;
			if (endIndex > data.length()) {
				result.add(data.substring(startIndex).trim());
			} else {
				result.add(data.substring(startIndex, endIndex).trim());
			}
			startIndex = endIndex;
		}

		weatherDto.setTime(result.get(0));
		weatherDto.setStn(result.get(1));
		weatherDto.setTemp(Double.parseDouble(result.get(2)));
		weatherDto.setDewpt(Double.parseDouble(result.get(3)));
		weatherDto.setWind(result.get(4));
		weatherDto.setVsby(Double.parseDouble(result.get(5)));
		weatherDto.setSkyWeather(result.get(6));

		return weatherDto;
	}
}
