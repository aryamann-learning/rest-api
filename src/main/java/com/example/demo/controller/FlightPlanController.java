package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.FlightPlanHeaderDto;
import com.example.demo.dto.FlightPlanOOOITimeDto;
import com.example.demo.service.FlightPlanService;

@RestController
@RequestMapping("/flight-service")
public class FlightPlanController {

	@Autowired
	FlightPlanService flightPlanService;

	@GetMapping("/flightplan/header/info")
	public FlightPlanHeaderDto parseFlightHeader() {
		return flightPlanService.parseFlightHeader();
	}

	@GetMapping("/flightplan/schedule/info")
	public FlightPlanOOOITimeDto parseFlightOOOITimes() {
		return flightPlanService.parseFlightOOOITimes();
	}
}
