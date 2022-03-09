package com.example.demo.dto;

public class WeatherDto {
	private String time;
	private String stn;
	private Double temp;
	private Double dewpt;
	private String wind;
	private Double vsby;
	private String sky;
	private String weather;
	private String queryTime;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStn() {
		return stn;
	}

	public void setStn(String stn) {
		this.stn = stn;
	}

	public Double getTemp() {
		return temp;
	}

	public void setTemp(Double temp) {
		this.temp = temp;
	}

	public Double getDewpt() {
		return dewpt;
	}

	public void setDewpt(Double dewpt) {
		this.dewpt = dewpt;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public Double getVsby() {
		return vsby;
	}

	public void setVsby(Double vsby) {
		this.vsby = vsby;
	}

	public String getSky() {
		return sky;
	}

	public void setSky(String sky) {
		this.sky = sky;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
	}

}
