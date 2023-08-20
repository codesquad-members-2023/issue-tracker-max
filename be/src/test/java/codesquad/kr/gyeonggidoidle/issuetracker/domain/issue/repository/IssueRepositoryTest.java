package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.RepositoryTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.result.IssueDetailResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@RepositoryTest
class IssueRepositoryTest {

    private IssueRepository repository;

    @Autowired
    public IssueRepositoryTest(NamedParameterJdbcTemplate template) {
        this.repository = new IssueRepository(template);
    }

    @DisplayName("issueId로 IssueDetailResult를 가져올 수 있다.")
    @Test
    void getIssueDetailByIssueId() {
        //govem
        Long issueId = 2L;

        //when
        IssueDetailResult actual = repository.findByIssueId(issueId);

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual.getId()).isEqualTo(2L);
            assertions.assertThat(actual.getAuthor()).isEqualTo("joy");
            assertions.assertThat(actual.getIsOpen()).isFalse();
        });
    }
}
