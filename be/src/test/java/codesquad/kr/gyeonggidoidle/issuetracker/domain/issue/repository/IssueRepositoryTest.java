package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.RepositoryTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.vo.IssueVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
public class IssueRepositoryTest {

    private final IssueRepository repository;

    @Autowired
    public IssueRepositoryTest(DataSource dataSource) {
        this.repository = new IssueRepository(dataSource);
    }

    @DisplayName("DB에서 열린 이슈를 불러온다.")
    @Test
    void openIssuetest() {
        List<IssueVO> actualValue = repository.findOpenIssues();

        assertThat(actualValue.size()).isEqualTo(2);
        assertThat(actualValue.get(1).getTitle()).isEqualTo("제목 1");
        assertThat(actualValue.get(0).getAuthor()).isEqualTo("ati");
    }

    @DisplayName("DB에서 닫힌 이슈를 불러온다.")
    @Test
    void closedIssuetest() {
        List<IssueVO> actualValue = repository.findClosedIssues();

        assertThat(actualValue.size()).isEqualTo(3);
        assertThat(actualValue.get(0).getId()).isEqualTo(5);
        assertThat(actualValue.get(2).getMilestone()).isEqualTo("마일스톤 2");

    }
}
