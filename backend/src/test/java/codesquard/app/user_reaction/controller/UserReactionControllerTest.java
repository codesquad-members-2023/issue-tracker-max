package codesquard.app.user_reaction.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import codesquard.app.ControllerTestSupport;
import codesquard.app.api.errors.exception.NoSuchReactionException;

class UserReactionControllerTest extends ControllerTestSupport {

	@DisplayName("이슈 사용자 반응을 등록한다.")
	@Test
	void createIssue() throws Exception {
		// given
		int reactionId = 1;
		int issueId = 1;

		// when & then
		mockMvc.perform(post("/api/reactions/" + reactionId + "/issues/" + issueId))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.savedUserReactionId").exists())
			.andDo(print());
	}

	@DisplayName("이슈 사용자 반응 등록에 실패한다.")
	@Test
	void createIssue_Fail() throws Exception {
		// given
		int reactionId = 1;
		int issueId = 1;
		willThrow(new NoSuchReactionException())
			.given(userReactionService).saveIssueReaction(anyLong(), anyLong(), anyLong());

		// when & then
		mockMvc.perform(post("/api/reactions/" + reactionId + "/issues/" + issueId))
			.andExpect(status().isNotFound())
			.andDo(print());
	}

	@DisplayName("댓글 사용자 반응을 등록한다.")
	@Test
	void createComment() throws Exception {
		// given
		int reactionId = 1;
		int commentId = 1;

		// when & then
		mockMvc.perform(post("/api/reactions/" + reactionId + "/comments/" + commentId))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.savedUserReactionId").exists())
			.andDo(print());
	}

	@DisplayName("댓글 사용자 반응 등록에 실패한다.")
	@Test
	void createComment_Fail() throws Exception {
		// given
		int reactionId = 1;
		int commentId = 1;
		willThrow(new NoSuchReactionException())
			.given(userReactionService).saveCommentReaction(anyLong(), anyLong(), anyLong());

		// when & then
		mockMvc.perform(post("/api/reactions/" + reactionId + "/comments/" + commentId))
			.andExpect(status().isNotFound())
			.andDo(print());
	}
}
