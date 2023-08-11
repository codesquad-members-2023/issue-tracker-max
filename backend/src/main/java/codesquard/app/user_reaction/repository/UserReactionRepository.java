package codesquard.app.user_reaction.repository;

public interface UserReactionRepository {
	Long saveIssueReaction(Long reactionId, Long userId, Long issueId);

	Long saveCommentReaction(Long reactionId, Long userId, Long commentId);

	void delete(Long id);

	boolean isExistReaction(Long reactionId);

	boolean isExistUserReaction(Long id);
}
