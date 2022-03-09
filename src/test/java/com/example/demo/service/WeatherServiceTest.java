package com.example.demo.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.SpringBootTestConfig;
import com.example.demo.dto.WeatherDto;

public class WeatherServiceTest extends SpringBootTestConfig {
	@Autowired
	WeatherService weatherService;

	@Test
	public void testParseWeatherXml() {
		String xml = "<![CDATA[ TIME STN TEMP DEWPT WIND VSBY SKY/CURRENT WEATHER ---- ---- ---- ----- ------------- -------- --------------------- 0454 KMEM 43 31 NNE 12 G 18 10 Cloudy Query Time: Mar 08 2022 05:31:46 GMT End Of Text.]]> ";
		WeatherDto dto = weatherService.parseWeatherXml(xml);
		Assert.assertNotNull(dto);
	}
}
