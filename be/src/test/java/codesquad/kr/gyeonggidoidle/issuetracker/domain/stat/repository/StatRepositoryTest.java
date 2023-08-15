package codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.RepositoryTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.IssueByMilestoneVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.MilestoneStatVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.StatVO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@RepositoryTest
class StatRepositoryTest {

    private StatRepository repository;

    @Autowired
    public StatRepositoryTest(NamedParameterJdbcTemplate template) {
        this.repository = new StatRepository(template);
    }

    @DisplayName("전체적인 통계 정보를 가져온다.")
    @Test
    void getOverallStats() {
        //when
        StatVO actual = repository.countOverallStats();

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual.getLabelCount()).isEqualTo(4);
            assertions.assertThat(actual.getMilestoneCount()).isEqualTo(4);
            assertions.assertThat(actual.getClosedIssueCount()).isEqualTo(3);
        });
    }

    @DisplayName("열린 마일스톤 개수와 닫힌 마일스톤 개수를 반환한다.")
    @Test
    void countMilestoneStats() {
        //when
        MilestoneStatVO actual = repository.countMilestoneStats();

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual.getOpenMilestoneCount()).isEqualTo(3);
            assertions.assertThat(actual.getCloseMilestoneCount()).isEqualTo(1);
        });
    }

    @DisplayName("총 마일스톤 개수와 라벨 개수를 반환한다.")
    @Test
    void countLabelStats() {
        //when
        StatVO actual = repository.countLabelStats();

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual.getOpenIssueCount()).isNull();
            assertions.assertThat(actual.getClosedIssueCount()).isNull();
            assertions.assertThat(actual.getMilestoneCount()).isEqualTo(4);
            assertions.assertThat(actual.getLabelCount()).isEqualTo(4);
        });
    }

    @DisplayName("마일스톤 당 열린 이슈와 닫힌 이슈 개수를 반환한다.")
    @Test
    void countIssuesCountByMilestoneIds() {
        //when
        Map<Long, IssueByMilestoneVO> actual = repository.countIssuesByMilestoneIds(List.of(1L, 2L, 3L));

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual).hasSize(3);
            assertions.assertThat(actual.get(1L).getOpenIssueCount()).isEqualTo(1);
            assertions.assertThat(actual.get(1L).getClosedIssueCount()).isEqualTo(2);
            assertions.assertThat(actual.get(2L).getOpenIssueCount()).isEqualTo(0);
            assertions.assertThat(actual.get(2L).getClosedIssueCount()).isEqualTo(1);
            assertions.assertThat(actual.get(3L).getOpenIssueCount()).isEqualTo(1);
            assertions.assertThat(actual.get(3L).getClosedIssueCount()).isEqualTo(0);
        });
    }
}
