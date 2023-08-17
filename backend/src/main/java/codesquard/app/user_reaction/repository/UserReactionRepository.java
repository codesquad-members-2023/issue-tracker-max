package codesquard.app.user_reaction.repository;

import java.util.List;

import codesquard.app.issue.dto.response.userReactionResponse;

public interface UserReactionRepository {
	Long saveIssueReaction(Long reactionId, Long userId, Long issueId);

	Long saveCommentReaction(Long reactionId, Long userId, Long commentId);

	List<userReactionResponse> findIssueReactionBy(Long issueId, Long userId);

	void delete(Long id);

	boolean isExistReaction(Long reactionId);

	boolean isExistUserReaction(Long id);

	boolean isSameUserReactionAuthor(Long id, Long userId);
}
