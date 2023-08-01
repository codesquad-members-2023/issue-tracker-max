package kr.codesquad.issuetracker.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.issuetracker.domain.Comment;
import kr.codesquad.issuetracker.infrastructure.persistence.CommentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;

	@Transactional
	public void register(Integer userId, String content, Integer issueId) {
		Comment comment = new Comment(content, userId, issueId);
		commentRepository.save(comment);
	}
}
