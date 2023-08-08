package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.RepositoryTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneDetailsVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@RepositoryTest
class MilestoneRepositoryTest {

    private MilestoneRepository repository;

    @Autowired
    public MilestoneRepositoryTest(NamedParameterJdbcTemplate template) {
        this.repository = new MilestoneRepository(template);
    }

    @DisplayName("열린 마일스톤을 이름 순으로 가져온다.")
    @Test
    void findOpenMilestones() {
        //when
        List<MilestoneDetailsVO> actual = repository.findOpenMilestones();

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual).hasSize(3);
            assertions.assertThat(actual.get(0).getName()).isEqualTo("마일스톤 0");
            assertions.assertThat(actual.get(1).getName()).isEqualTo("마일스톤 1");
            assertions.assertThat(actual.get(2).getName()).isEqualTo("마일스톤 3");
        });
    }

    @DisplayName("모든 마일스톤 정보를 이름 순으로 가지고 온다.")
    @Test
    void findAll() {
        //when
        List<MilestoneDetailsVO> actual = repository.findAllFilters();

        //then
        assertSoftly(assertions -> {
            assertThat(actual).hasSize(4);
            assertThat(actual.get(0).getName()).isEqualTo("마일스톤 0");
            assertThat(actual.get(1).getName()).isEqualTo("마일스톤 1");
            assertThat(actual.get(2).getName()).isEqualTo("마일스톤 2");
            assertThat(actual.get(3).getName()).isEqualTo("마일스톤 3");
        });
    }
}
