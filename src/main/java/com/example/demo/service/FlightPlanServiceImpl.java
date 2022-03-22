package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.constants.DemoConstants;
import com.example.demo.dto.FlightCapacityDetailsDto;
import com.example.demo.dto.FlightFuelTimeDetailsDto;
import com.example.demo.dto.FlightPlanInfoDto;
import com.example.demo.dto.FlightWeightParametersDto;
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

	private String getPDFCurrentLine(String completeText, String containsText) {
		String fltPlan = completeText.substring(completeText.indexOf(containsText));
		String[] lines = fltPlan.split(DemoConstants.LINE_SEPERATOR_REGEX);
		return lines[0];
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
		FlightFuelTimeDetailsDto flightFuelTimeDetailsDto = new FlightFuelTimeDetailsDto();
		flightFuelTimeDetailsDto.setDestination(getFuelTime("DEST"));
		flightFuelTimeDetailsDto.setAlternate(getFuelTime("ALTN"));
		flightFuelTimeDetailsDto.setReserve(getFuelTime("RES"));
		flightFuelTimeDetailsDto.setMelFuel(getFuelTime("MEL"));
		flightFuelTimeDetailsDto.setCont(getFuelTime("CONT"));
		flightFuelTimeDetailsDto.setRqr(getFuelTime("-RQR-"));
		flightFuelTimeDetailsDto.setRpf(getFuelTime("RPF"));
		flightFuelTimeDetailsDto.setCapt(getFuelTime("CAPT"));
		flightFuelTimeDetailsDto.setOther(getFuelTime("OTHER"));
		flightFuelTimeDetailsDto.setTkof(getFuelTime("-TKOF-"));
		flightFuelTimeDetailsDto.setTaxi(getFuelTime("TAXI"));
		flightFuelTimeDetailsDto.setTotal(getFuelTime("TOTAL"));
		flightFuelTimeDetailsDto.setFod(getFuelTime("FOD"));
		flightPlanInfoDto.setFuelTimeDetails(flightFuelTimeDetailsDto);
		return flightPlanInfoDto;
	}

	private FuelTime getFuelTime(String containsText) {
		int startIndex = 8;
		String completeText = getPDFCompleteText();
		String fuelTime = completeText.substring(completeText.indexOf("TIME    FUEL"),
				completeText.indexOf("REMARKS:"));
		String fuelTimeInfo = getPDFCurrentLine(fuelTime, containsText);
		FuelTime fuelTimeDto = new FuelTime();
		fuelTimeDto.setTime(fuelTimeInfo.substring(startIndex, startIndex + 5).trim());
		fuelTimeDto.setFuel(fuelTimeInfo.substring(startIndex + 8, startIndex + 13).trim());
		return fuelTimeDto;
	}

	private FlightPlanInfoDto parseFlightWeightDetails(FlightPlanInfoDto flightPlanInfoDto) {
		String completeText = getPDFCompleteText();
		FlightWeightParametersDto flightWeightParametersDto = new FlightWeightParametersDto();

		String basicOperationWeightDto = getPDFCurrentLine(completeText, "BOW");
		int startIndex = 10;
		flightWeightParametersDto
				.setBasicOperationWeight(basicOperationWeightDto.substring(startIndex, startIndex + 6).trim());

		String payLoadDto = getPDFCurrentLine(completeText, "PYLD");
		flightWeightParametersDto.setPayLoad(payLoadDto.substring(startIndex, startIndex + 6).trim());

		String zeroFuelWeightDto = getPDFCurrentLine(completeText, "ZFW");
		flightWeightParametersDto.setZeroFuelWeight(zeroFuelWeightDto.substring(startIndex, startIndex + 6).trim());

		String wbZeroFuelWeight = getPDFCurrentLine(completeText, "W/B ZFW");
		flightWeightParametersDto.setWbZeroFuelWeight(wbZeroFuelWeight.substring(startIndex, startIndex + 6).trim());

		String takeOff = getPDFCurrentLine(completeText, "TKOF");
		flightWeightParametersDto.setTakeOff(takeOff.substring(startIndex, startIndex + 6).trim());

		String takeOffGrossWeight = getPDFCurrentLine(completeText, "TOGW");
		flightWeightParametersDto
				.setTakeoffGrossWeight(takeOffGrossWeight.substring(startIndex, startIndex + 6).trim());

		String launchGrossWeight = getPDFCurrentLine(completeText, "LGW");
		flightWeightParametersDto.setLaunchGrossWeight(launchGrossWeight.substring(startIndex, startIndex + 6).trim());

		String localMeanTimeGrossWeight = getPDFCurrentLine(completeText, "LMTOGW");
		flightWeightParametersDto
				.setLocalMeanTimeGrossWeight(localMeanTimeGrossWeight.substring(startIndex, startIndex + 6).trim());
		flightPlanInfoDto.setWeightDetails(flightWeightParametersDto);
		return flightPlanInfoDto;
	}

	private FlightPlanInfoDto parseFlightCapacityDetails(FlightPlanInfoDto flightPlanInfoDto) {
		String completeText = getPDFCompleteText();
		FlightCapacityDetailsDto flightCapacityDetailsDto = new FlightCapacityDetailsDto();

		String indexDto = getPDFCurrentLine(completeText, "INDEX");
		int startIndex = 9;
		flightCapacityDetailsDto.setIndex(indexDto.substring(startIndex, startIndex + 6).trim());

		String maxZeroFuelWeightDto = getPDFCurrentLine(completeText, "MAXZFW");
		flightCapacityDetailsDto
				.setMaxZeroFuelWeight(maxZeroFuelWeightDto.substring(startIndex, startIndex + 6).trim());

		String minFltwDto = getPDFCurrentLine(completeText, "MINFLTW");
		flightCapacityDetailsDto.setMinFltw(minFltwDto.substring(startIndex, startIndex + 6).trim());

		String maxTakeOffGrossWeightDto = getPDFCurrentLine(completeText, "MAXTOGW");
		flightCapacityDetailsDto
				.setMaxTakeOffGrossWeight(maxTakeOffGrossWeightDto.substring(startIndex, startIndex + 6).trim());

		String maxLaunchGrossWeightDto = getPDFCurrentLine(completeText, "MAXLGW");
		flightCapacityDetailsDto
				.setMaxLaunchGrossWeight(maxLaunchGrossWeightDto.substring(startIndex, startIndex + 6).trim());

		String tankCapDto = getPDFCurrentLine(completeText, "TANKCAP");
		flightCapacityDetailsDto.setTankCap(tankCapDto.substring(startIndex, startIndex + 6).trim());

		String maxRmpwDto = getPDFCurrentLine(completeText, "MAXRMPW");
		flightCapacityDetailsDto.setMaxRmpw(maxRmpwDto.substring(startIndex, startIndex + 6).trim());

		flightPlanInfoDto.setCapacityDetails(flightCapacityDetailsDto);

		return flightPlanInfoDto;
	}

	private FlightPlanInfoDto parseFlightRemarksDetails(FlightPlanInfoDto flightPlanInfoDto) {
		String completeText = getPDFCompleteText();
		String remarksText = completeText.substring(completeText.indexOf("REMARKS:"),
				completeText.indexOf("ATIS/CLEARANCE") - 6);
		String[] remarks = remarksText.split(DemoConstants.LINE_DOT_SEPERATOR_REGEX);
		ArrayList<String> result = new ArrayList<>();
		for (int i = 1; i < remarks.length; i++) {
			result.add(remarks[i].replaceAll(DemoConstants.PAGE_NO_REGEX, ""));
		}
		flightPlanInfoDto.setRemarksDetails(result);
		return flightPlanInfoDto;
	}

	@Override
	public FlightPlanInfoDto getFlightInfo() {
		FlightPlanInfoDto flightPlanInfoDto = new FlightPlanInfoDto();
		flightPlanInfoDto = parseFlightHeader(flightPlanInfoDto);
		flightPlanInfoDto = parseFlightOOOITimes(flightPlanInfoDto);
		flightPlanInfoDto = parseFlightFuelDetails(flightPlanInfoDto);
		flightPlanInfoDto = parseFlightWeightDetails(flightPlanInfoDto);
		flightPlanInfoDto = parseFlightCapacityDetails(flightPlanInfoDto);
		flightPlanInfoDto = parseFlightRemarksDetails(flightPlanInfoDto);
		return flightPlanInfoDto;
	}
}
