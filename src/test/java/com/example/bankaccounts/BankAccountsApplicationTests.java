package com.example.bankaccounts;

import com.example.bankaccounts.payload.request.LoginRequest;
import com.example.bankaccounts.payload.request.SendMoneyRequest;
import com.example.bankaccounts.payload.response.JWTTokenSuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BankAccountsApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;


	@Test
	void checkTransfer() throws Exception {

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setPassword("123");
		loginRequest.setUsername("nik");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/singin")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(loginRequest)))
				.andExpect(status().isOk())
				.andReturn();

		String responseJson = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		JWTTokenSuccessResponse jwtTokenResponse = objectMapper.readValue(responseJson, JWTTokenSuccessResponse.class);

		String jwt = jwtTokenResponse.getToken();

		SendMoneyRequest request = new SendMoneyRequest();
		request.setAmount(100);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/user/transfer/1")
						.header(HttpHeaders.AUTHORIZATION, jwt)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk());
	}

}
