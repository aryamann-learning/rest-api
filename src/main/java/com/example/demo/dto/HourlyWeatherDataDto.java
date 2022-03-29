package com.example.demo.dto;

public class HourlyWeatherDataDto {
	private String hourlyForecast;
	private String temperature;
	private String windDirection;
	private String windSpeed;
	private String windGust;
	private String precipChance;
	private String ceilingHeight;
	private String visibility;

	public String getHourlyForecast() {
		return hourlyForecast;
	}

	public void setHourlyForecast(String hourlyForecast) {
		this.hourlyForecast = hourlyForecast;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	public String getWindGust() {
		return windGust;
	}

	public void setWindGust(String windGust) {
		this.windGust = windGust;
	}

	public String getPrecipChance() {
		return precipChance;
	}

	public void setPrecipChance(String precipChance) {
		this.precipChance = precipChance;
	}

	public String getCeilingHeight() {
		return ceilingHeight;
	}

	public void setCeilingHeight(String ceilingHeight) {
		this.ceilingHeight = ceilingHeight;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

}
