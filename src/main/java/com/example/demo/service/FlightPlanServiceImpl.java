package com.example.demo.service;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.constants.DemoConstants;
import com.example.demo.dto.FlightPlanInfoDto;

@Service
public class FlightPlanServiceImpl implements FlightPlanService {

	private String getPDFCompleteText() {
		try {
			File file = new File(DemoConstants.PDF_PATH);
			PDDocument inputPdf = PDDocument.load(file);
			PDFTextStripper pdfReader = new PDFTextStripper();
			String completeText = pdfReader.getText(inputPdf);
			inputPdf.close();
			return completeText;
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while processing pdf file");
		}
	}

	private FlightPlanInfoDto parseFlightHeader(FlightPlanInfoDto flightPlanInfoDto) {
		String completeText = getPDFCompleteText();
		String fltPlanData = getPDFNextLine(completeText, DemoConstants.FLT_HEADER);

		int startIndex = 0;
		flightPlanInfoDto.setFlightNumber(fltPlanData.substring(startIndex, 8).trim());
		startIndex += 8;
		flightPlanInfoDto.setLegDate(Double.parseDouble(fltPlanData.substring(startIndex, startIndex + 3).trim()));
		startIndex += 3;
		flightPlanInfoDto.setOrigin(fltPlanData.substring(startIndex, startIndex + 6).trim());
		startIndex += 6;
		flightPlanInfoDto.setDestination(fltPlanData.substring(startIndex, startIndex + 6).trim());
		startIndex += 6;
		flightPlanInfoDto.setAlternate(fltPlanData.substring(startIndex, startIndex + 6).trim());
		startIndex += 6;
		flightPlanInfoDto.setEquipment(fltPlanData.substring(startIndex, startIndex + 9).trim());
		startIndex += 9;
		flightPlanInfoDto.setAircraftNumber(fltPlanData.substring(startIndex, startIndex + 9).trim());
		startIndex += 9;
		flightPlanInfoDto.setCruiseValue(Double.parseDouble(fltPlanData.substring(startIndex, startIndex + 5).trim()));
		startIndex += 5;
		flightPlanInfoDto.setWindValue(fltPlanData.substring(startIndex, startIndex + 5).trim());
		startIndex += 5;
		flightPlanInfoDto.setRouteCode(fltPlanData.substring(startIndex, startIndex + 3).trim());
		flightPlanInfoDto.setCompleteText(completeText);
		return flightPlanInfoDto;
	}

	private String getPDFNextLine(String completeText, String containsText) {
		String fltPlan = completeText.substring(completeText.indexOf(containsText));
		String[] lines = fltPlan.split(DemoConstants.LINE_SEPERATOR_REGEX);
		return lines[1];
	}

	private FlightPlanInfoDto parseFlightOOOITimes(FlightPlanInfoDto flightPlanInfoDto) {
		String completeText = getPDFCompleteText();

		int startIndex = 0;
		String schFltOutData = getPDFNextLine(completeText, "OUT/OFF");
		startIndex = 60;
		flightPlanInfoDto.setSchOutTime(schFltOutData.substring(startIndex, startIndex + 4).trim());
		startIndex += 5;
		flightPlanInfoDto.setSchOffTime(schFltOutData.substring(startIndex, startIndex + 4).trim());

		String schFltInData = getPDFNextLine(completeText, "ON/IN");
		startIndex = 60;
		flightPlanInfoDto.setSchOnTime(schFltInData.substring(startIndex, startIndex + 4).trim());
		startIndex += 5;
		flightPlanInfoDto.setSchInTime(schFltInData.substring(startIndex, startIndex + 4));

		String schFltData = getPDFNextLine(completeText, "FLT/BLK");
		startIndex = 60;
		flightPlanInfoDto.setSchFltTime(schFltData.substring(startIndex, startIndex + 4).trim());
		startIndex += 5;
		flightPlanInfoDto.setSchBlkTime(schFltData.substring(startIndex, startIndex + 4));

		return flightPlanInfoDto;
	}

	@Override
	public FlightPlanInfoDto getFlightInfo() {
		FlightPlanInfoDto flightPlanInfoDto = new FlightPlanInfoDto();
		return parseFlightOOOITimes(parseFlightHeader(flightPlanInfoDto));
	}
}
