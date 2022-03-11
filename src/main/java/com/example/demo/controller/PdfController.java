package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.PdfService;

@RestController
@RequestMapping("/pdf-service")
public class PdfController {

	@Autowired
	PdfService pdfService;

	@GetMapping("/pdf/fps")
	public String getPdfInfo() {
		return pdfService.parsePdf();

	}

}
