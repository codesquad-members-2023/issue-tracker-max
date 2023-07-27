package codesquard.app.issue.comment.repository;

import java.util.List;

import codesquard.app.issue.comment.entity.Comment;

public interface CommentRepository {

	Long save(Comment comment);

	List<Comment> findAll();

	Comment findById(Long id);

	Long modify(Comment comment);

	Long deleteById(Long id);
}
