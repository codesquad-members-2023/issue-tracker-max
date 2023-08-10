package codesquard.app.user_reaction.repository;

public interface UserReactionRepository {
	Long saveIssueReaction(Long reactionId, Long userId, Long issueId);

	Long saveCommentReaction(Long reactionId, Long userId, Long commentId);

	boolean isExist(Long reactionId);
}
