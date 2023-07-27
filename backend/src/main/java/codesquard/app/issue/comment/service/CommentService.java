package codesquard.app.issue.comment.service;

import org.springframework.stereotype.Service;

import codesquard.app.issue.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
}
