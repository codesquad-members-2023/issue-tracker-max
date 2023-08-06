package codesquard.app.comment.controller;

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

import codesquard.app.ControllerTestSupport;
import codesquard.app.comment.controller.request.CommentModifyRequest;
import codesquard.app.comment.controller.request.CommentSaveRequest;

class CommentControllerTest extends ControllerTestSupport {

	@DisplayName("새로운 댓글을 등록한다.")
	@Test
	void saveComment() throws Exception {
		// given
		CommentSaveRequest request = new CommentSaveRequest(1L, 1L, "controller comment");

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
		CommentSaveRequest request = new CommentSaveRequest(1L, 1L, content);

		// when // then
		mockMvc.perform(post("/api/comments")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.content").value("내용은 필수입니다."));
	}

	@DisplayName("등록된 댓글을 수정한다.")
	@Test
	void modify() throws Exception {
		// given
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
		CommentModifyRequest request = new CommentModifyRequest(content);

		// when // then
		mockMvc.perform(patch("/api/comments/1")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.content").value("내용은 필수입니다."));
	}

	@DisplayName("등록된 댓글을 삭제한다.")
	@Test
	void test() throws Exception {
		// when // then
		mockMvc.perform(delete("/api/comments/1"))
			.andDo(print())
			.andExpect(status().isOk());
	}

}
