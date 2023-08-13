package codesquad.issueTracker.comment.service;

import codesquad.issueTracker.comment.domain.Comment;
import codesquad.issueTracker.comment.dto.CommentRequestDto;
import codesquad.issueTracker.comment.dto.CommentResponseDto;
import codesquad.issueTracker.comment.repository.CommentRepository;
import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;
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

    @Transactional
    public Long save(Long userId, Long issueId, CommentRequestDto commentRequestDto) {
        return commentRepository.create(userId, issueId, commentRequestDto)
                .orElseThrow(() -> new CustomException(ErrorCode.FAILED_CREATE_COMMENT));
    }

    @Transactional
    public Long modify(Long commentId, CommentRequestDto commentRequestDto) {
        validateExistComment(commentId);
        validateCommentStatus(commentId);
        return commentRepository.update(commentId, commentRequestDto)
                .orElseThrow(() -> new CustomException(ErrorCode.FAILED_UPDATE_COMMENT));
    }

    @Transactional
    public Long delete(Long commentId) {
        validateExistComment(commentId);
        validateCommentStatus(commentId);
        return commentRepository.deleteById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.FAILED_DELETE_COMMENT));
    }

    private Comment validateExistComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_COMMENT));
    }

    private Comment validateCommentStatus(Long commentId) {
        return commentRepository.findExistCommentById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.ALREADY_DELETED_COMMENT));
    }
}
