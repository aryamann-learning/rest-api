package com.example.demo.service;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.constants.DemoConstants;

@Service
public class PdfServiceImpl implements PdfService {

	@Override
	public String parsePdf() {
		String contents = null;
		File file = new File(DemoConstants.PDF_PATH);
		try {
			PDDocument inputPdf = PDDocument.load(file);
			PDFTextStripper pdfReader = new PDFTextStripper();
			contents = pdfReader.getText(inputPdf);
			inputPdf.close();
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while processing pdf file");
		}
		return contents;
	}
}