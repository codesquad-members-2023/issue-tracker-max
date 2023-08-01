package codesquard.app.user.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import codesquard.app.ControllerTestSupport;
import codesquard.app.user.controller.request.UserSaveRequest;

class UserRestControllerTest extends ControllerTestSupport {
	@Test
	@DisplayName("회원정보가 주어지고 회원가입 요청을 했을때 회원이 등록된다")
	public void createUser() throws Exception {
		// given
		UserSaveRequest userSaveRequest = new UserSaveRequest();
		// when then
		mockMvc.perform(post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userSaveRequest))
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(jsonPath("success").value(equalTo(true)));
	}
}
