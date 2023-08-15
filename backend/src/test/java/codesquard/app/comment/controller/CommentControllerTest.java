package codesquard.app.comment.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import codesquard.app.ControllerTestSupport;
import codesquard.app.api.errors.handler.GlobalExceptionHandler;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.comment.controller.request.CommentModifyRequest;
import codesquard.app.comment.controller.request.CommentSaveRequest;

class CommentControllerTest extends ControllerTestSupport {

	@DisplayName("새로운 댓글을 등록한다.")
	@Test
	void saveComment() throws Exception {
		// given
		mockingAuthenticateUser();
		CommentSaveRequest request = new CommentSaveRequest(1L, "controller comment");

		// when // then
		mockMvc.perform(post("/api/comments")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated());
	}

	private static Stream<Arguments> provideInvalidContent() {
		return Stream.of(
			Arguments.of((Object)null),
			Arguments.of(""),
			Arguments.of(" ")
		);
	}

	@DisplayName("빈 댓글을 등록하려는 경우 예외가 발생한다.")
	@MethodSource("provideInvalidContent")
	@ParameterizedTest
	void saveInvalidComment(String content) throws Exception {
		// given
		mockingAuthenticateUser();
		CommentSaveRequest request = new CommentSaveRequest(1L, content);

		// when // then
		mockMvc.perform(post("/api/comments")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("Bad Request"))
			.andExpect(jsonPath("$.data[*].field").value("content"))
			.andExpect(jsonPath("$.data[*].defaultMessage").value("내용은 필수입니다."));
	}

	@DisplayName("등록된 댓글을 수정한다.")
	@Test
	void modify() throws Exception {
		// given
		mockingAuthenticateUser();
		CommentModifyRequest request = new CommentModifyRequest("controller comment");

		// when // then
		mockMvc.perform(patch("/api/comments/1")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@DisplayName("댓글 수정 시 내용이 비어있는 경우 예외가 발생한다.")
	@MethodSource("provideInvalidContent")
	@ParameterizedTest
	void modifyInvalidComment(String content) throws Exception {
		// given
		mockingAuthenticateUser();
		CommentModifyRequest request = new CommentModifyRequest(content);

		// when // then
		mockMvc.perform(patch("/api/comments/1")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("Bad Request"))
			.andExpect(jsonPath("$.data[*].field").value("content"))
			.andExpect(jsonPath("$.data[*].defaultMessage").value("내용은 필수입니다."));
	}

	@DisplayName("등록된 댓글을 삭제한다.")
	@Test
	void test() throws Exception {
		// given
		mockingAuthenticateUser();

		// when // then
		mockMvc.perform(delete("/api/comments/1"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	private void mockingAuthenticateUser() {
		mockMvc = MockMvcBuilders.standaloneSetup(new CommentController(commentService))
			.setControllerAdvice(new GlobalExceptionHandler())
			.setCustomArgumentResolvers(loginUserArgumentResolver)
			.build();

		AuthenticateUser authenticateUser = new AuthenticateUser(1L, "user", "user@email.com", null);
		when(loginUserArgumentResolver.supportsParameter(any()))
			.thenReturn(true);
		when(loginUserArgumentResolver.resolveArgument(any(), any(), any(), any()))
			.thenReturn(authenticateUser);
	}

}
