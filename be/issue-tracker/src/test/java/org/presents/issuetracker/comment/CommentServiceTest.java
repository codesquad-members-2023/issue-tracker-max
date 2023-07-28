package org.presents.issuetracker.comment;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.presents.issuetracker.annotation.ServiceTest;
import org.presents.issuetracker.comment.repository.CommentRepository;
import org.presents.issuetracker.comment.service.CommentService;

@ServiceTest
public class CommentServiceTest {
    @InjectMocks
    CommentService commentService;

    @Mock
    CommentRepository commentRepository;

}
