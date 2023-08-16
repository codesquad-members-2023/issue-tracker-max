package codesquard.app.label.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import codesquard.app.api.response.LabelResponseMessage;
import codesquard.app.label.entity.Label;
import codesquard.app.label_milestone.ControllerTestSupport;

class LabelControllerTest extends ControllerTestSupport {

	@DisplayName("라벨을 등록한다.")
	@Test
	void save() throws Exception {
		// given
		Label label = new Label("라벨명", "dark", "#ffffff", "라벨 설명");

		// when // then
		mockMvc.perform(post("/api/labels")
				.content(objectMapper.writeValueAsString(label))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.code").value("201"))
			.andExpect(jsonPath("$.status").value("CREATED"))
			.andExpect(jsonPath("$.message").value(LabelResponseMessage.LABEL_SAVE_SUCCESS))
			.andExpect(jsonPath("$.data.id").isNotEmpty());
	}
}
