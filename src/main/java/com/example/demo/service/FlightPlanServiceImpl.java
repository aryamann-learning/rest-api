package com.example.demo.service;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.constants.DemoConstants;
import com.example.demo.dto.FlightFuelTimeDetailsDto;
import com.example.demo.dto.FlightPlanInfoDto;
import com.example.demo.dto.FuelTime;

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

	private String getPDFNextLine(String completeText, String containsText) {
		String fltPlan = completeText.substring(completeText.indexOf(containsText));
		String[] lines = fltPlan.split(DemoConstants.LINE_SEPERATOR_REGEX);
		return lines[1];
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

	private FlightPlanInfoDto parseFlightFuelDetails(FlightPlanInfoDto flightPlanInfoDto) {
		String completeText = getPDFCompleteText();
		String fltPlan = completeText.substring(completeText.indexOf("TIME    FUEL"));
		String[] lines = fltPlan.split(DemoConstants.LINE_SEPERATOR_REGEX);

		int startIndex = 8;
		FuelTime destinationDto = getFuelTime(lines, 1, startIndex);
		FuelTime alternateDto = getFuelTime(lines, 2, startIndex);
		FuelTime reserveDto = getFuelTime(lines, 3, startIndex);
		FuelTime melFuelDto = getFuelTime(lines, 4, startIndex);
		FuelTime contDto = getFuelTime(lines, 5, startIndex);
		FuelTime rqrDto = getFuelTime(lines, 7, startIndex);
		FuelTime rpfDto = getFuelTime(lines, 9, startIndex);
		FuelTime captDto = getFuelTime(lines, 10, startIndex);
		FuelTime otherDto = getFuelTime(lines, 11, startIndex);
		FuelTime tkofDto = getFuelTime(lines, 12, startIndex);
		FuelTime taxiDto = getFuelTime(lines, 13, startIndex);
		FuelTime totalDto = getFuelTime(lines, 14, startIndex);
		FuelTime fodDto = getFuelTime(lines, 16, startIndex);
		
		FlightFuelTimeDetailsDto flightFuelTimeDetailsDto = new FlightFuelTimeDetailsDto();
		flightFuelTimeDetailsDto.setDestination(destinationDto);
		flightFuelTimeDetailsDto.setAlternate(alternateDto);
		flightFuelTimeDetailsDto.setReserve(reserveDto);
		flightFuelTimeDetailsDto.setMelFuel(melFuelDto);
		flightFuelTimeDetailsDto.setCont(contDto);
		flightFuelTimeDetailsDto.setRqr(rqrDto);
		flightFuelTimeDetailsDto.setRpf(rpfDto);
		flightFuelTimeDetailsDto.setCapt(captDto);
		flightFuelTimeDetailsDto.setOther(otherDto);
		flightFuelTimeDetailsDto.setTkof(tkofDto);
		flightFuelTimeDetailsDto.setTaxi(taxiDto);
		flightFuelTimeDetailsDto.setTotal(totalDto);
		flightFuelTimeDetailsDto.setFod(fodDto);
		flightPlanInfoDto.setFuelTimeDetails(flightFuelTimeDetailsDto);
		return flightPlanInfoDto;
	}

	private FuelTime getFuelTime(String[] lines, int lineNumber, int startIndex) {
		String fuelTimeInfo = lines[lineNumber];
		FuelTime fuelTimeDto = new FuelTime();
		fuelTimeDto.setTime(fuelTimeInfo.substring(startIndex, startIndex + 5).trim());
		fuelTimeDto.setFuel(fuelTimeInfo.substring(startIndex + 8, startIndex + 13).trim());
		return fuelTimeDto;
	}

	@Override
	public FlightPlanInfoDto getFlightInfo() {
		FlightPlanInfoDto flightPlanInfoDto = new FlightPlanInfoDto();
		flightPlanInfoDto = parseFlightHeader(flightPlanInfoDto);
		flightPlanInfoDto = parseFlightOOOITimes(flightPlanInfoDto);
		flightPlanInfoDto = parseFlightFuelDetails(flightPlanInfoDto);
		return flightPlanInfoDto;
	}
}
