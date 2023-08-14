package com.issuetrackermax.controller.label;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.issuetrackermax.controller.ControllerTestSupport;
import com.issuetrackermax.controller.label.dto.request.LabelPostRequest;
import com.issuetrackermax.controller.label.dto.response.LabelDetailResponse;

class LabelControllerTest extends ControllerTestSupport {

	@DisplayName("저장된 레이블을 반환한다.")
	@Test
	void show() throws Exception {
		// given
		LabelDetailResponse labelDetailResponse = LabelDetailResponse.builder()
			.id(1L)
			.title("title")
			.description("description")
			.textColor("0#1111")
			.backgroundColor("0#2222")
			.build();
		LabelDetailResponse labelDetailResponse2 = LabelDetailResponse.builder()
			.id(2L)
			.title("title2")
			.description("description2")
			.textColor("0#3333")
			.backgroundColor("0#4444")
			.build();

		when(labelService.getLabelList()).thenReturn(List.of(labelDetailResponse, labelDetailResponse2));
		when(milestoneService.getMilestoneCount()).thenReturn(3L);

		// when & then
		mockMvc.perform(
				get("/api/labels")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value("true"))
			.andExpect(jsonPath("$.data.milestoneCount").value(3L))
			.andExpect(jsonPath("$.data.labels[0].id").value(1L))
			.andExpect(jsonPath("$.data.labels[0].title").value("title"))
			.andExpect(jsonPath("$.data.labels[0].description").value("description"))
			.andExpect(jsonPath("$.data.labels[0].textColor").value("0#1111"))
			.andExpect(jsonPath("$.data.labels[0].backgroundColor").value("0#2222"))
			.andExpect(jsonPath("$.data.labels[1].id").value(2L))
			.andExpect(jsonPath("$.data.labels[1].title").value("title2"))
			.andExpect(jsonPath("$.data.labels[1].description").value("description2"))
			.andExpect(jsonPath("$.data.labels[1].textColor").value("0#3333"))
			.andExpect(jsonPath("$.data.labels[1].backgroundColor").value("0#4444"));

	}

	@DisplayName("title, description, textColr, backgrounColor를 입력하면 등록된다.")
	@Test
	void postLabel() throws Exception {

		// given
		LabelPostRequest labelPostRequest = LabelPostRequest.builder()
			.title("title1")
			.description("description")
			.textColor("0#1111")
			.backgroundColor("0#2222")
			.build();

		when(labelService.save(any())).thenReturn(1L);

		// when & then
		mockMvc.perform(
				post("/api/labels")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(labelPostRequest))
			)
			.andDo(print())
			.andExpect(jsonPath("$.success").value("true"))
			.andExpect(jsonPath("$.data.id").value(1L));
	}

}