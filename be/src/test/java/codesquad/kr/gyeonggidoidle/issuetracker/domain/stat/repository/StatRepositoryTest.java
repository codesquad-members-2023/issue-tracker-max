package codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.RepositoryTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.StatVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
public class StatRepositoryTest {

    private StatRepository repository;

    @Autowired
    public StatRepositoryTest(DataSource dataSource) {
        this.repository = new StatRepository(dataSource);
    }

    @DisplayName("전체적인 통계 정보를 가져온다.")
    @Test
    void getOverallStatsTest() {
        StatVO actual = repository.countOverallStats();

        assertThat(actual.getLabelCount()).isEqualTo(3);
        assertThat(actual.getMilestoneCount()).isEqualTo(3);
        assertThat(actual.getClosedIssueCount()).isEqualTo(3);
    }
}
