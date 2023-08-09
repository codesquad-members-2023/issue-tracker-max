package codesquad.issueTracker.comment.service;

import codesquad.issueTracker.comment.dto.CommentResponseDto;
import codesquad.issueTracker.comment.repository.CommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<CommentResponseDto> getComments(Long issueId) {
        return commentRepository.findByIssueId(issueId);
    }
}
