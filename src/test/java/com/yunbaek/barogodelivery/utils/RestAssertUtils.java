package com.yunbaek.barogodelivery.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class RestAssertUtils {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private RestAssertUtils() {
	}

	public static String extractResponse(ResultActions actions) throws Exception {
		return actions
			.andReturn()
			.getResponse()
			.getContentAsString();
	}

	public static <T> ResultActions doPost(MockMvc mockMvc, String uri, T request) throws Exception {
		return mockMvc.perform(post(uri)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(MAPPER.writeValueAsString(request)));
	}

	public static String mapAsString(Object object) throws JsonProcessingException {
		return MAPPER.writeValueAsString(object);
	}
}
