package codesquard.app.label.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import codesquard.app.ControllerTestSupport;
import codesquard.app.label.dto.LabelRequest;

class LabelControllerTest extends ControllerTestSupport {

	@DisplayName("라벨을 등록한다.")
	@Test
	void save() throws Exception {
		// given
		LabelRequest labelRequest = new LabelRequest("라벨명", "#000000", "#ffffff", "라벨 설명");

		// when // then
		mockMvc.perform(post("/api/labels")
				.content(objectMapper.writeValueAsString(labelRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.success").value(true));
	}
}
