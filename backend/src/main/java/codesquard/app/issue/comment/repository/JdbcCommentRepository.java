package codesquard.app.issue.comment.repository;

import java.util.List;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import codesquard.app.issue.comment.entity.Comment;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class JdbcCommentRepository implements CommentRepository {

	private final NamedParameterJdbcTemplate template;

	@Override
	public Long save(Comment comment) {
		return null;
	}

	@Override
	public List<Comment> findAll() {
		return null;
	}

	@Override
	public Comment findById(Long id) {
		return null;
	}

	@Override
	public Long modify(Comment comment) {
		return null;
	}

	@Override
	public Long deleteById(Long id) {
		return null;
	}
}
