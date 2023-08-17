package codesquard.app.user_reaction.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.api.errors.errorcode.IssueErrorCode;
import codesquard.app.api.errors.exception.NoSuchReactionException;
import codesquard.app.api.errors.exception.NoSuchUserReactionException;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.user_reaction.repository.UserReactionRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserReactionQueryService {

	private final UserReactionRepository userReactionRepository;

	public void validateExistReactionId(Long reactionId) {
		if (!userReactionRepository.isExistReaction(reactionId)) {
			throw new NoSuchReactionException();
		}
	}

	public void validateExistUserReactionId(Long userReactionId) {
		if (!userReactionRepository.isExistUserReaction(userReactionId)) {
			throw new NoSuchUserReactionException();
		}
	}

	public void validateUserReactionAuthor(Long id, Long userId) {
		validateExistUserReactionId(id);
		if (!userReactionRepository.isSameUserReactionAuthor(id, userId)) {
			throw new RestApiException(IssueErrorCode.FORBIDDEN_USER_REACTION);
		}
	}
}
