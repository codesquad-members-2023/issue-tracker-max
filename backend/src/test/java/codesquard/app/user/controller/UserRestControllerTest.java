package codesquard.app.user.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

import codesquard.app.ControllerTestSupport;
import codesquard.app.user.controller.request.UserSaveRequest;
import codesquard.app.user.service.request.UserSaveServiceRequest;
import codesquard.app.user.service.response.UserSaveResponse;

class UserRestControllerTest extends ControllerTestSupport {
	@Test
	@DisplayName("회원정보가 주어지고 회원가입 요청을 했을때 회원이 등록된다")
	public void createUser() throws Exception {
		// given
		UserSaveRequest userSaveRequest = new UserSaveRequest("hong1234", "hong1234@gmail.com", "hong1234", "hong1234");
		UserSaveResponse userSaveResponse = new UserSaveResponse(true);
		// mocking
		when(userService.signUp(Mockito.any(UserSaveServiceRequest.class))).thenReturn(userSaveResponse);
		// when then
		mockMvc.perform(post("/api/users")
				.content(objectMapper.writeValueAsString(userSaveRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("success").value(equalTo(true)));
	}
}
