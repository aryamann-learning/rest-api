package com.example.demo.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dao.AirportweatherDao;
import com.example.demo.dto.HourlyWeatherDataDto;

@Service
public class BlendProductsServiceImpl implements BlendProductService {

	public static final String SPLIT_BY_3CHARS_REGEX = "(?<=\\G.{3})";
	@Autowired
	private WebClient webClient;
	@Autowired
	private AirportweatherDao daoAirportWeather;

	private String getWebClientResponse(String url) {
		return webClient.get().uri(url).retrieve().bodyToMono(String.class).block();
	}

	@Override
	public Map<String, List<HourlyWeatherDataDto>> getAirportWeatherInfo(String date, String cc) {
		String url = getUrl(date, cc);
		String response = getWebClientResponse(url);
		return parseBlendText(response, date);
	}

	private Map<String, List<HourlyWeatherDataDto>> parseBlendText(String response, String date) {
		String[] utc = null, tmp = null, wdr = null, wsp = null, gst = null, p01 = null, cig = null, vis = null;
		String line = "";
		LinkedHashMap<String, List<HourlyWeatherDataDto>> mapHourlyWeatherData = new LinkedHashMap<>();
		try (InputStream stream = new ByteArrayInputStream(response.getBytes(Charset.forName("UTF-8")));
				BufferedReader br = new BufferedReader(new InputStreamReader(stream));) {
			line = br.readLine();
			line = br.readLine(); // skip 1st two lines
			line = br.readLine().trim();
			String airport = line.substring(0, line.indexOf(" "));
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty()) {
					List<HourlyWeatherDataDto> lstHourlyData = getLstHourlyData(airport, date, utc, tmp, wdr, wsp, gst,
							p01, cig, vis);
					mapHourlyWeatherData.put(airport, lstHourlyData);
					if ((line = br.readLine()) != null) {
						line = line.trim();
						airport = line.substring(0, line.indexOf(" "));
					}
				} else {
					if (line.contains("UTC")) {
						utc = getHourlyData(line);
					} else if (line.contains("TMP")) {
						tmp = getHourlyData(line);
					} else if (line.contains("WDR")) {
						wdr = getHourlyData(line);
					} else if (line.contains("WSP")) {
						wsp = getHourlyData(line);
					} else if (line.contains("GST")) {
						gst = getHourlyData(line);
					} else if (line.contains("P01")) {
						p01 = getHourlyData(line);
					} else if (line.contains("CIG")) {
						cig = getHourlyData(line);
					} else if (line.contains("VIS")) {
						vis = getHourlyData(line);
					}
				}
			}

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return mapHourlyWeatherData;
	}

	private List<HourlyWeatherDataDto> getLstHourlyData(String airport, String date, String[] utc, String[] tmp,
			String[] wdr, String[] wsp, String[] gst, String[] p01, String[] cig, String[] vis) {
		List<HourlyWeatherDataDto> lstHourlyData = new ArrayList<>();
		for (int i = 0; i < utc.length; i++) {
			HourlyWeatherDataDto hourlyWeatherDataDto = new HourlyWeatherDataDto();
			hourlyWeatherDataDto.setHourlyForecast(Integer.parseInt(utc[i].trim()));
			hourlyWeatherDataDto.setTemperature(Integer.parseInt(tmp[i].trim()));
			hourlyWeatherDataDto.setWindDirection(Integer.parseInt(wdr[i].trim()));
			hourlyWeatherDataDto.setWindSpeed(Integer.parseInt(wsp[i].trim()));
			hourlyWeatherDataDto.setWindGust(Integer.parseInt(gst[i].trim()));
			hourlyWeatherDataDto.setPrecipChance(Integer.parseInt(p01[i].trim()));
			hourlyWeatherDataDto.setCeilingHeight(Integer.parseInt(cig[i].trim()));
			hourlyWeatherDataDto.setVisibility(Integer.parseInt(vis[i].trim()));
			lstHourlyData.add(hourlyWeatherDataDto);
			daoAirportWeather.saveAirportWeatherData(airport, Integer.parseInt(date),
					hourlyWeatherDataDto);
		}
		return lstHourlyData;
	}

	private String getUrl(String date, String cc) {
		String url = "https://nomads.ncep.noaa.gov/pub/data/nccf/com/blend/prod/blend.:date/:cc/text/blend_nbhtx.t:ccz";
		url = url.replace(":date", date).replace(":cc", cc);
		return url;
	}

	private String[] getHourlyData(String line) {
		String arrLine = line.substring(4, line.length());
		return arrLine.split(SPLIT_BY_3CHARS_REGEX);
	}
}
