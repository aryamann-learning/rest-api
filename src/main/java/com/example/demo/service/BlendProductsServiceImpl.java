package com.example.demo.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.AirportWeatherForecastDto;

@Service
public class BlendProductsServiceImpl implements BlendProductService {

	@Autowired
	private WebClient webClient;

	private String getWebClientResponse(String url) {
		return webClient.get().uri(url).retrieve().bodyToMono(String.class).block();
	}

	@Override
	public List<AirportWeatherForecastDto> getAirportWeatherInfo(String date, String cc) {
		String url = getUrl(date, cc);
		String response = getWebClientResponse(url);
		return parseBlendText(response);
	}

	private List<AirportWeatherForecastDto> parseBlendText(String response) {
		String line = "";
		List<AirportWeatherForecastDto> airportsWeatherForecast = new ArrayList<>();
		AirportWeatherForecastDto airportWeatherForecastDto = new AirportWeatherForecastDto();
		try (InputStream stream = new ByteArrayInputStream(response.getBytes(Charset.forName("UTF-8")));
				BufferedReader br = new BufferedReader(new InputStreamReader(stream));) {
			line = br.readLine();
			line = br.readLine(); // skip 1st two lines
			line = br.readLine();
			airportWeatherForecastDto.setAirport(line);
			line = br.readLine();

			while (line != null) {
				if (line.trim().isEmpty()) {
					airportsWeatherForecast.add(airportWeatherForecastDto);
					airportWeatherForecastDto = new AirportWeatherForecastDto();
					line = br.readLine();
					airportWeatherForecastDto.setAirport(line);
				} else {
					if (line.contains("UTC")) {
						airportWeatherForecastDto.setHourlyForecast(line);
					} else if (line.contains("TMP")) {
						airportWeatherForecastDto.setTemperature(line);
					} else if (line.contains("WDR")) {
						airportWeatherForecastDto.setWindDirection(line);
					} else if (line.contains("WSP")) {
						airportWeatherForecastDto.setWindSpeed(line);
					} else if (line.contains("GST")) {
						airportWeatherForecastDto.setWindGust(line);
					} else if (line.contains("P01")) {
						airportWeatherForecastDto.setPrecipChance(line);
					} else if (line.contains("CIG")) {
						airportWeatherForecastDto.setCeilingHeight(line);
					} else if (line.contains("VIS")) {
						airportWeatherForecastDto.setVisibility(line);
					}
				}

				line = br.readLine();
			}
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return airportsWeatherForecast;
	}

	private String getUrl(String date, String cc) {
		String url = "https://nomads.ncep.noaa.gov/pub/data/nccf/com/blend/prod/blend.:date/:cc/text/blend_nbhtx.t:ccz";
		url = url.replace(":date", date).replace(":cc", cc);
		return url;
	}
}
