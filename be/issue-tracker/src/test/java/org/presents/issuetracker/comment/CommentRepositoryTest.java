package org.presents.issuetracker.comment;

import org.presents.issuetracker.annotation.RepositoryTest;
import org.presents.issuetracker.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository repository;
}
