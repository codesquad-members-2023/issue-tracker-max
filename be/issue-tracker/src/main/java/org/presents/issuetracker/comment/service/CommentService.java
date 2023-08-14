package org.presents.issuetracker.comment.service;

import lombok.RequiredArgsConstructor;
import org.presents.issuetracker.comment.dto.request.CommentCreateRequest;
import org.presents.issuetracker.comment.dto.response.CommentResponse;
import org.presents.issuetracker.comment.entity.Comment;
import org.presents.issuetracker.comment.repository.CommentRepository;
import org.presents.issuetracker.user.dto.response.UserResponse;
import org.presents.issuetracker.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

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
}
