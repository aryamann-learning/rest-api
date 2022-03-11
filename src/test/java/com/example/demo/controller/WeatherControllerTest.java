package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.example.demo.SpringBootTestConfig;

public class WeatherControllerTest extends SpringBootTestConfig {
	@Test
	public void testGetWeatherInfo() throws Exception {
		mockMvc.perform(get("/weather-service/weather/info").contentType(MediaType.APPLICATION_XML)).andExpect(status().isOk());
	}

}
