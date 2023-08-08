package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.RepositoryTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.vo.IssueVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@RepositoryTest
class IssueRepositoryTest {

    private final IssueRepository repository;

    @Autowired
    IssueRepositoryTest(NamedParameterJdbcTemplate template) {
        this.repository = new IssueRepository(template);
    }

    @DisplayName("DB에서 열린 이슈를 불러온다.")
    @Test
    void findOpenIssue() {
        //when
        List<IssueVO> actual = repository.findOpenIssues();

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual).hasSize(2);
            assertions.assertThat(actual.get(1).getTitle()).isEqualTo("제목 1");
            assertions.assertThat(actual.get(0).getAuthor()).isEqualTo("ati");
        });
    }

    @DisplayName("DB에서 닫힌 이슈를 불러온다.")
    @Test
    void findClosedIssue() {
        //when
        List<IssueVO> actualValue = repository.findClosedIssues();

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actualValue.size()).isEqualTo(3);
            assertions.assertThat(actualValue.get(0).getId()).isEqualTo(5);
            assertions.assertThat(actualValue.get(2).getMilestone()).isEqualTo("마일스톤 2");
        });
    }
}
