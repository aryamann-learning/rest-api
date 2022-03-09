package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.SpringBootTestConfig;

public class BookRepositoryTest extends SpringBootTestConfig {
	@Autowired
	BookRepository bookRepository;

}
