package codesquad.issueTracker.comment.service;

import org.springframework.stereotype.Service;

import codesquad.issueTracker.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;
}
