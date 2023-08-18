package org.presents.issuetracker.comment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.presents.issuetracker.annotation.RepositoryTest;
import org.presents.issuetracker.comment.repository.CommentRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@RepositoryTest
public class CommentRepositoryTest {
    private static final long VALID_ID = 1L;
    private static final int NO_RECORD = 0;
    private static final int NOT_DELETED_FLAG = 0;

    @Mock
    DataSource dataSource;

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    private CommentRepository repository;

    @BeforeEach
    void setUp() {
        repository = new CommentRepository(jdbcTemplate, dataSource);
    }

    @Test
    @DisplayName("코멘트가 존재하면 true를 반환한다")
    void existsById_Exists_ReturnsTrue() {
        // given
        final Integer count = 1;
        final String sql = "SELECT COUNT(*) FROM comment WHERE comment_id = :id AND is_deleted = :not_deleted_flag";
        given(jdbcTemplate.queryForObject(
                sql,
                Map.of(
                        "id", VALID_ID,
                        "not_deleted_flag", NOT_DELETED_FLAG
                ),
                Integer.class)
        ).willReturn(count);

        // when
        boolean exists = repository.existsById(VALID_ID);

        // then
        assertTrue(exists);
    }

    @Test
    @DisplayName("코멘트가 존재하지 않으면 false를 리턴한다")
    void existsById_NotExists_ReturnsFalse() {
        // given
        final String sql = "SELECT COUNT(*) FROM comment WHERE comment_id = :id AND is_deleted = :not_deleted_flag";
        given(jdbcTemplate.queryForObject(
                sql,
                Map.of(
                        "id", VALID_ID,
                        "not_deleted_flag", NOT_DELETED_FLAG
                ),
                Integer.class)
        ).willReturn(NO_RECORD);

        // when
        boolean exists = repository.existsById(VALID_ID);

        // then
        assertFalse(exists);
    }
}
