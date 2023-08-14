package org.presents.issuetracker.comment.service;

import lombok.RequiredArgsConstructor;
import org.presents.issuetracker.comment.dto.request.CommentCreateRequest;
import org.presents.issuetracker.comment.dto.response.CommentResponse;
import org.presents.issuetracker.comment.dto.response.CommentUpdateRequest;
import org.presents.issuetracker.comment.entity.Comment;
import org.presents.issuetracker.comment.repository.CommentRepository;
import org.presents.issuetracker.global.dto.response.IdResponse;
import org.presents.issuetracker.global.error.exception.CustomException;
import org.presents.issuetracker.global.error.statuscode.CommentErrorCode;
import org.presents.issuetracker.user.dto.response.UserResponse;
import org.presents.issuetracker.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponse create(CommentCreateRequest commentCreateRequest) {
        Long id = commentRepository.save(Comment.builder()
                .issueId(commentCreateRequest.getIssueId())
                .authorId(commentCreateRequest.getAuthorId())
                .contents(commentCreateRequest.getContents())
                .build());

        Comment comment = commentRepository.findById(id);

        return CommentResponse.of(
                comment.getId(),
                comment.getContents(),
                comment.getCreatedAt(),
                UserResponse.from(userRepository.findById(comment.getAuthorId())));
    }

    @Transactional
    public IdResponse update(CommentUpdateRequest commentUpdateRequest) {
        validateId(commentUpdateRequest.getId());
        commentRepository.update(Comment.of(commentUpdateRequest.getId(), commentUpdateRequest.getContents()));

        return IdResponse.from(commentUpdateRequest.getId());
    }

    @Transactional
    public void delete(Long id) {
        validateId(id);
        commentRepository.deleteById(id);
    }

    private void validateId(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new CustomException(CommentErrorCode.NOT_FOUND);
        }
    }
}
