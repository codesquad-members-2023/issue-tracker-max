package codesquard.app.user_reaction.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.comment.service.CommentService;
import codesquard.app.issue.service.IssueQueryService;
import codesquard.app.user_reaction.repository.UserReactionRepository;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class UserReactionService {

	private final UserReactionRepository userReactionRepository;
	private final UserReactionQueryService userReactionQueryService;
	private final IssueQueryService issueQueryService;
	private final CommentService commentService;

	public Long saveIssueReaction(Long reactionId, Long userId, Long issueId) {
		userReactionQueryService.validateExistReactionId(reactionId);
		issueQueryService.validateExistIssue(issueId);
		return userReactionRepository.saveIssueReaction(reactionId, userId, issueId);
	}

	public Long saveCommentReaction(Long reactionId, Long userId, Long commentId) {
		userReactionQueryService.validateExistReactionId(reactionId);
		commentService.validateCommentId(commentId);
		return userReactionRepository.saveCommentReaction(reactionId, userId, commentId);
	}

	public void deleteIssueReaction(Long id) {
		userReactionQueryService.validateExistUserReactionId(id);
		userReactionRepository.delete(id);
	}
}
