package codesquard.app.user_reaction.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.api.errors.exception.NoSuchReactionException;
import codesquard.app.user_reaction.repository.UserReactionRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserReactionQueryService {

	private final UserReactionRepository userReactionRepository;

	public void validateExistReactionId(Long reactionId) {
		if (!userReactionRepository.isExist(reactionId)) {
			throw new NoSuchReactionException();
		}
	}
}
