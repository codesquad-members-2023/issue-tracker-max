package org.presents.issuetracker.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.presents.issuetracker.label.dto.request.LabelCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ExceptionTest {
	@Autowired
	private MockMvc mockMvc;

	// Custom Exception 테스트 추가 예정

	@Test
	@DisplayName("잘못된 페이지에 접근 시도 했을 때 예외가 발생하는지 확인한다.")
	void testNoHandlerFoundException() throws Exception {
		mockMvc.perform(get("/nonexistent"))
			.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("유효하지 않은 형식(이름 입력X)을 입력했을 때 예외가 발생하는지 확인한다.")
	void testInvalidInput() throws Exception {
		LabelCreateRequest requestBody = new LabelCreateRequest(null, "설명", "#FFFFFF", "dark");

		mockMvc.perform(post("/labels")
				.content(asJsonString(requestBody))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
