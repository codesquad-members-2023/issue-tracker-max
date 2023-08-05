package codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.RepositoryTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.IssueByMilestoneVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.MilestoneStatVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.StatVO;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        assertThat(actual.getLabelCount()).isEqualTo(4);
        assertThat(actual.getMilestoneCount()).isEqualTo(4);
        assertThat(actual.getClosedIssueCount()).isEqualTo(3);
    }

    @DisplayName("열린 마일스톤 개수와 닫힌 마일스톤 개수를 반환한다.")
    @Test
    void testCountMilestoneStats() {
        MilestoneStatVO actual = repository.countMilestoneStats();

        assertThat(actual.getOpenMilestoneCount()).isEqualTo(3);
        assertThat(actual.getCloseMilestoneCount()).isEqualTo(1);
    }

    @DisplayName("총 마일스톤 개수와 라벨 개수를 반환한다.")
    @Test
    void testCountLabelStats() {
        StatVO actual = repository.countLabelStats();

        assertThat(actual.getOpenIssueCount()).isNull();
        assertThat(actual.getClosedIssueCount()).isNull();
        assertThat(actual.getMilestoneCount()).isEqualTo(4);
        assertThat(actual.getLabelCount()).isEqualTo(4);
    }

    @DisplayName("마일스톤 당 열린 이슈와 닫힌 이슈 개수를 반환한다.")
    @Test
    void testFindIssuesCountByMilestoneIds() {
        Map<Long, IssueByMilestoneVO> actual = repository.findIssuesCountByMilestoneIds(List.of(1L,2L,3L));

        assertThat(actual.size()).isEqualTo(3);

        assertThrows(NullPointerException.class, ()->{
            actual.get(0L).getOpenIssueCount();
        });
        assertThrows(NullPointerException.class, ()->{
            actual.get(0L).getClosedIssueCount();
        });

        assertThat(actual.get(1L).getOpenIssueCount()).isEqualTo(1);
        assertThat(actual.get(1L).getClosedIssueCount()).isEqualTo(2);

        assertThat(actual.get(2L).getOpenIssueCount()).isEqualTo(0);
        assertThat(actual.get(2L).getClosedIssueCount()).isEqualTo(1);

        assertThat(actual.get(3L).getOpenIssueCount()).isEqualTo(1);
        assertThat(actual.get(3L).getClosedIssueCount()).isEqualTo(0);
    }
}
