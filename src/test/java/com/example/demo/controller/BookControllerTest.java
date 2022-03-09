package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.example.demo.SpringBootTestConfig;

public class BookControllerTest extends SpringBootTestConfig {
	@Test
	public void testGetBooks() throws Exception {
		mockMvc.perform(get("/books/db/info").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}
