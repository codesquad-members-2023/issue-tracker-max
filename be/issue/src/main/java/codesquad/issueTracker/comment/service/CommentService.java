package codesquad.issueTracker.comment.service;

import codesquad.issueTracker.comment.dto.CommentRequestDto;
import codesquad.issueTracker.comment.dto.CommentResponseDto;
import codesquad.issueTracker.comment.repository.CommentRepository;
import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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

    @Transactional
    public Long save(HttpServletRequest request, Long issueId, CommentRequestDto commentRequestDto) {
        Long userId = Long.parseLong(String.valueOf(request.getAttribute("userId")));
        return commentRepository.create(userId, issueId, commentRequestDto)
                .orElseThrow(() -> new CustomException(ErrorCode.DB_EXCEPTION));
    }

    @Transactional
    public Long modify(HttpServletRequest request, Long commentId, CommentRequestDto commentRequestDto) {
        Long userId = Long.parseLong(String.valueOf(request.getAttribute("userId")));

        return commentRepository.update(userId, commentId, commentRequestDto)
                    .orElseThrow(() -> new CustomException(ErrorCode.DB_EXCEPTION));
    }
}
