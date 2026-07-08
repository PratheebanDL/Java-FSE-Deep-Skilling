package com.cognizant.springlearn.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void helloWithoutName_returnsDefaultGreeting() throws Exception {
		mockMvc.perform(get("/hello"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello, World!"));
	}

	@Test
	void helloWithName_returnsPersonalizedGreeting() throws Exception {
		mockMvc.perform(get("/hello").param("name", "Cognizant"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello, Cognizant!"));
	}
}
