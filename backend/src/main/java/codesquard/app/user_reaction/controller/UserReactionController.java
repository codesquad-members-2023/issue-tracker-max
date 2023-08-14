package codesquard.app.user_reaction.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.api.response.ApiResponse;
import codesquard.app.api.response.ResponseMessage;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.user.annotation.Login;
import codesquard.app.user_reaction.dto.response.UserReactionDeleteResponse;
import codesquard.app.user_reaction.dto.response.UserReactionSaveResponse;
import codesquard.app.user_reaction.service.UserReactionService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(path = "/api/reactions")
@RestController
public class UserReactionController {

	private final UserReactionService userReactionService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/{reactionId}/issues/{issueId}")
	public ApiResponse<UserReactionSaveResponse> saveIssueReaction(@PathVariable Long reactionId,
		@PathVariable Long issueId, @Login AuthenticateUser user) {
		Long userId = user.toEntity().getId();
		Long userReactionId = userReactionService.saveIssueReaction(reactionId, userId, issueId);
		return ApiResponse.of(HttpStatus.CREATED, ResponseMessage.USER_REACTION_ISSUE_SAVE_SUCCESS,
			UserReactionSaveResponse.success(userReactionId));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/{reactionId}/comments/{commentId}")
	public ApiResponse<UserReactionSaveResponse> saveCommentReaction(@PathVariable Long reactionId,
		@PathVariable Long commentId, @Login AuthenticateUser user) {
		Long userId = user.toEntity().getId();
		Long userReactionId = userReactionService.saveCommentReaction(reactionId, userId, commentId);
		return ApiResponse.of(HttpStatus.CREATED, ResponseMessage.USER_REACTION_COMMENT_SAVE_SUCCESS,
			UserReactionSaveResponse.success(userReactionId));
	}

	@DeleteMapping("/{userReactionId}")
	public ApiResponse<UserReactionDeleteResponse> deleteIssueReaction(@PathVariable Long userReactionId) {
		userReactionService.deleteIssueReaction(userReactionId);
		return ApiResponse.ok(UserReactionDeleteResponse.success(userReactionId));
	}
}
