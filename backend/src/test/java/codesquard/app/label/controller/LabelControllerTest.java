package codesquard.app.label.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import codesquard.app.api.errors.handler.GlobalExceptionHandler;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.label.dto.request.LabelSaveRequest;
import codesquard.app.label.dto.request.LabelUpdateRequest;
import codesquard.app.label_milestone.ControllerTestSupport;

class LabelControllerTest extends ControllerTestSupport {

	@DisplayName("라벨을 등록한다.")
	@Test
	void saveLabel() throws Exception {
		// given
		mockingAuthenticateUser();
		LabelSaveRequest labelSaveRequest = new LabelSaveRequest("이름", "light", "#040404", "설명");

		// when // then
		mockMvc.perform(post("/api/labels")
				.content(objectMapper.writeValueAsString(labelSaveRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated());
	}

	@DisplayName("라벨을 수정한다.")
	@Test
	void updateLabel() throws Exception {
		// given
		mockingAuthenticateUser();
		LabelUpdateRequest labelUpdateRequest = new LabelUpdateRequest("바뀐 이름", "dark", "#111104", "바뀐 설명");

		// when // then
		mockMvc.perform(put("/api/labels/1")
				.content(objectMapper.writeValueAsString(labelUpdateRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@DisplayName("라벨을 삭제한다.")
	@Test
	void deleteLabel() throws Exception {
		// given
		mockingAuthenticateUser();

		// when // then
		mockMvc.perform(delete("/api/labels/1"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	private void mockingAuthenticateUser() {
		mockMvc = MockMvcBuilders.standaloneSetup(new LabelController(labelService))
			.setControllerAdvice(new GlobalExceptionHandler())
			.setCustomArgumentResolvers(loginUserArgumentResolver)
			.build();

		AuthenticateUser user = new AuthenticateUser(1L, "user", "user@email.com", null);
		when(loginUserArgumentResolver.supportsParameter(any()))
			.thenReturn(true);
		when(loginUserArgumentResolver.resolveArgument(any(), any(), any(), any()))
			.thenReturn(user);
	}
}
