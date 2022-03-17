package com.example.demo.dto;

public class FlightPlanInfoDto {
	private String flightNumber;
	private Double legDate;
	private String origin;
	private String destination;
	private String alternate;
	private String equipment;
	private String aircraftNumber;
	private Double cruiseValue;
	private String windValue;
	private String routeCode;
	private String schOutTime;
	private String schOffTime;
	private String schOnTime;
	private String schInTime;
	private String schFltTime;
	private String schBlkTime;
	private FlightFuelTimeDetailsDto fuelTimeDetails;
	private FlightWeightParametersDto weightDetails;
	private String completeText;

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public Double getLegDate() {
		return legDate;
	}

	public void setLegDate(Double legDate) {
		this.legDate = legDate;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getAlternate() {
		return alternate;
	}

	public void setAlternate(String alternate) {
		this.alternate = alternate;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getAircraftNumber() {
		return aircraftNumber;
	}

	public void setAircraftNumber(String aircraftNumber) {
		this.aircraftNumber = aircraftNumber;
	}

	public Double getCruiseValue() {
		return cruiseValue;
	}

	public void setCruiseValue(Double cruiseValue) {
		this.cruiseValue = cruiseValue;
	}

	public String getWindValue() {
		return windValue;
	}

	public void setWindValue(String windValue) {
		this.windValue = windValue;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getSchOutTime() {
		return schOutTime;
	}

	public void setSchOutTime(String schOutTime) {
		this.schOutTime = schOutTime;
	}

	public String getSchOffTime() {
		return schOffTime;
	}

	public void setSchOffTime(String schOffTime) {
		this.schOffTime = schOffTime;
	}

	public String getSchOnTime() {
		return schOnTime;
	}

	public void setSchOnTime(String schOnTime) {
		this.schOnTime = schOnTime;
	}

	public String getSchInTime() {
		return schInTime;
	}

	public void setSchInTime(String schInTime) {
		this.schInTime = schInTime;
	}

	public String getSchFltTime() {
		return schFltTime;
	}

	public void setSchFltTime(String schFltTime) {
		this.schFltTime = schFltTime;
	}

	public String getSchBlkTime() {
		return schBlkTime;
	}

	public void setSchBlkTime(String schBlkTime) {
		this.schBlkTime = schBlkTime;
	}

	public FlightFuelTimeDetailsDto getFuelTimeDetails() {
		return fuelTimeDetails;
	}

	public void setFuelTimeDetails(FlightFuelTimeDetailsDto fuelTimeDetails) {
		this.fuelTimeDetails = fuelTimeDetails;
	}

	public FlightWeightParametersDto getWeightDetails() {
		return weightDetails;
	}

	public void setWeightDetails(FlightWeightParametersDto weightDetails) {
		this.weightDetails = weightDetails;
	}

	public String getCompleteText() {
		return completeText;
	}

	public void setCompleteText(String completeText) {
		this.completeText = completeText;
	}

}
