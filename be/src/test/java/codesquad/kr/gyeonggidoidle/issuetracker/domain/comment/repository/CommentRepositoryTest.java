package codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.RepositoryTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.Comment;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.repository.result.CommentResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@RepositoryTest
public class CommentRepositoryTest {

    private CommentRepository repository;

    @Autowired
    public CommentRepositoryTest(NamedParameterJdbcTemplate template) {
        this.repository = new CommentRepository(template);
    }

    @DisplayName("issueId로 issue의 Comment들을 가져온다.")
    @Test
    void findByIssueId() {
        //given
        Long issueId = 4L;

        //when
        List<CommentResult> actual = repository.findByIssueId(issueId);

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual).hasSize(3);
            assertions.assertThat(actual.get(0).getContents()).isEqualTo("5번 댓글입니다.");
            assertions.assertThat(actual.get(1).getId()).isEqualTo(6L);
            assertions.assertThat(actual.get(2).getAuthorName()).isEqualTo("nag");
        });
    }

    @DisplayName("Comment를 저장한다.")
    @Test
    void saveComment() {
        //given
        Comment comment = Comment.builder()
                .authorId(1L)
                .issueId(2L)
                .contents("내용")
                .build();

        //when
        Long saveResult = repository.save(comment);

        //then
        assertThat(saveResult).isEqualTo(12L);
    }

    @DisplayName("Comment를 수정한다.")
    @Test
    void updateComment() {
        //given
        Comment comment = Comment.builder()
                .id(5L)
                .contents("변경 내용")
                .build();

        //when
        boolean result = repository.update(comment);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("Comment를 삭제한다.")
    @Test
    void deleteComment() {
        //given
        Long commentId = 3L;

        //when
        boolean result = repository.delete(commentId);

        //then
        assertThat(result).isTrue();
    }

}
