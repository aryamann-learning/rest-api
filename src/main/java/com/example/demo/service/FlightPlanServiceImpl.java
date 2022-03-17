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
		String destinationInfo = lines[1];
		FuelTime destinationDto = new FuelTime();
		destinationDto.setTime(destinationInfo.substring(startIndex, startIndex + 5).trim());
		destinationDto.setFuel(destinationInfo.substring(startIndex + 8, startIndex + 13).trim());

		String alternateInfo = lines[2];
		FuelTime alternateDto = new FuelTime();
		alternateDto.setTime(alternateInfo.substring(startIndex, startIndex + 5).trim());
		alternateDto.setFuel(alternateInfo.substring(startIndex + 8, startIndex + 13).trim());

		String reserveInfo = lines[3];
		FuelTime reserveDto = new FuelTime();
		reserveDto.setTime(reserveInfo.substring(startIndex, startIndex + 5).trim());
		reserveDto.setFuel(reserveInfo.substring(startIndex + 8, startIndex + 13).trim());

		String melFuelInfo = lines[4];
		FuelTime melFuelDto = new FuelTime();
		melFuelDto.setTime(melFuelInfo.substring(startIndex, startIndex + 5).trim());
		melFuelDto.setFuel(melFuelInfo.substring(startIndex + 8, startIndex + 13).trim());

		String contInfo = lines[5];
		FuelTime contDto = new FuelTime();
		contDto.setTime(contInfo.substring(startIndex, startIndex + 5).trim());
		contDto.setFuel(contInfo.substring(startIndex + 8, startIndex + 13).trim());

		String rqrInfo = lines[7];
		FuelTime rqrDto = new FuelTime();
		rqrDto.setTime(rqrInfo.substring(startIndex, startIndex + 5).trim());
		rqrDto.setFuel(rqrInfo.substring(startIndex + 8, startIndex + 13).trim());

		String rpfInfo = lines[9];
		FuelTime rpfDto = new FuelTime();
		rpfDto.setTime(rpfInfo.substring(startIndex, startIndex + 5).trim());
		rpfDto.setFuel(rpfInfo.substring(startIndex + 8, startIndex + 13).trim());

		String captInfo = lines[10];
		FuelTime captDto = new FuelTime();
		captDto.setTime(captInfo.substring(startIndex, startIndex + 5).trim());
		captDto.setFuel(captInfo.substring(startIndex + 8, startIndex + 13).trim());

		String otherInfo = lines[11];
		FuelTime otherDto = new FuelTime();
		otherDto.setTime(otherInfo.substring(startIndex, startIndex + 5).trim());
		otherDto.setFuel(otherInfo.substring(startIndex + 8, startIndex + 13).trim());

		String tkofInfo = lines[12];
		FuelTime tkofDto = new FuelTime();
		tkofDto.setTime(tkofInfo.substring(startIndex, startIndex + 5).trim());
		tkofDto.setFuel(tkofInfo.substring(startIndex + 8, startIndex + 13).trim());

		String taxiInfo = lines[13];
		FuelTime taxiDto = new FuelTime();
		taxiDto.setTime(taxiInfo.substring(startIndex, startIndex + 5).trim());
		taxiDto.setFuel(taxiInfo.substring(startIndex + 8, startIndex + 13).trim());

		String totalInfo = lines[14];
		FuelTime totalDto = new FuelTime();
		totalDto.setTime(totalInfo.substring(startIndex, startIndex + 5).trim());
		totalDto.setFuel(totalInfo.substring(startIndex + 8, startIndex + 13).trim());

		String fodInfo = lines[16];
		FuelTime fodDto = new FuelTime();
		fodDto.setTime(fodInfo.substring(startIndex, startIndex + 5).trim());
		fodDto.setFuel(fodInfo.substring(startIndex + 8, startIndex + 13).trim());

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

	@Override
	public FlightPlanInfoDto getFlightInfo() {
		FlightPlanInfoDto flightPlanInfoDto = new FlightPlanInfoDto();
		flightPlanInfoDto = parseFlightHeader(flightPlanInfoDto);
		flightPlanInfoDto = parseFlightOOOITimes(flightPlanInfoDto);
		flightPlanInfoDto = parseFlightFuelDetails(flightPlanInfoDto);
		return flightPlanInfoDto;
	}
}
