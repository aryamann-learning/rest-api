package com.example.demo.service;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.constants.DemoConstants;
import com.example.demo.dto.FlightPlanDto;

@Service
public class FlightPlanServiceImpl implements FlightPlanService {

	@Override
	public FlightPlanDto getFlightPlanInfo() {
		FlightPlanDto flightPlanDto = new FlightPlanDto();
		String completeText = null;
		File file = new File(DemoConstants.PDF_PATH);
		try {
			PDDocument inputPdf = PDDocument.load(file);
			PDFTextStripper pdfReader = new PDFTextStripper();
			completeText = pdfReader.getText(inputPdf);
			String fltPlan = completeText.substring(completeText.indexOf(DemoConstants.FLT_HEADER));
			String[] lines = fltPlan.split(DemoConstants.LINE_SEPERATOR_REGEX);
			String fltPlanData = lines[1];
			int startIndex = 0;
			flightPlanDto.setFlightNumber(fltPlanData.substring(startIndex, 8).trim());
			startIndex += 8;
			flightPlanDto.setLegDate(Double.parseDouble(fltPlanData.substring(startIndex, startIndex + 3).trim()));
			startIndex += 3;
			flightPlanDto.setOrigin(fltPlanData.substring(startIndex, startIndex + 6).trim());
			startIndex += 6;
			flightPlanDto.setDestination(fltPlanData.substring(startIndex, startIndex + 6).trim());
			startIndex += 6;
			flightPlanDto.setAlternate(fltPlanData.substring(startIndex, startIndex + 6).trim());
			startIndex += 6;
			flightPlanDto.setEquipment(fltPlanData.substring(startIndex, startIndex + 9).trim());
			startIndex += 9;
			flightPlanDto.setAircraftNumber(fltPlanData.substring(startIndex, startIndex + 9).trim());
			startIndex += 9;
			flightPlanDto.setCruiseValue(Double.parseDouble(fltPlanData.substring(startIndex, startIndex + 5).trim()));
			startIndex += 5;
			flightPlanDto.setWindValue(fltPlanData.substring(startIndex, startIndex + 5).trim());
			startIndex += 5;
			flightPlanDto.setRouteCode(fltPlanData.substring(startIndex, startIndex + 3).trim());
			flightPlanDto.setCompleteText(completeText);
			inputPdf.close();
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error while processing fightplan pdf file");
		}
		return flightPlanDto;
	}
}
