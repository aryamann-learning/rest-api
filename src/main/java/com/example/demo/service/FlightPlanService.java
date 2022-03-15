package com.example.demo.service;

import com.example.demo.dto.FlightPlanHeaderDto;
import com.example.demo.dto.FlightPlanOOOITimeDto;

public interface FlightPlanService {

	public FlightPlanHeaderDto parseFlightHeader();

	public FlightPlanOOOITimeDto parseFlightOOOITimes();

}
