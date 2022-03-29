package com.example.demo.dto;

public class HourlyWeatherDataDto {
	private Integer hourlyForecast;
	private Integer temperature;
	private Integer windDirection;
	private Integer windSpeed;
	private Integer windGust;
	private Integer precipChance;
	private Integer ceilingHeight;
	private Integer visibility;

	public Integer getHourlyForecast() {
		return hourlyForecast;
	}

	public void setHourlyForecast(Integer hourlyForecast) {
		this.hourlyForecast = hourlyForecast;
	}

	public Integer getTemperature() {
		return temperature;
	}

	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}

	public Integer getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(Integer windDirection) {
		this.windDirection = windDirection;
	}

	public Integer getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(Integer windSpeed) {
		this.windSpeed = windSpeed;
	}

	public Integer getWindGust() {
		return windGust;
	}

	public void setWindGust(Integer windGust) {
		this.windGust = windGust;
	}

	public Integer getPrecipChance() {
		return precipChance;
	}

	public void setPrecipChance(Integer precipChance) {
		this.precipChance = precipChance;
	}

	public Integer getCeilingHeight() {
		return ceilingHeight;
	}

	public void setCeilingHeight(Integer ceilingHeight) {
		this.ceilingHeight = ceilingHeight;
	}

	public Integer getVisibility() {
		return visibility;
	}

	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}

}
