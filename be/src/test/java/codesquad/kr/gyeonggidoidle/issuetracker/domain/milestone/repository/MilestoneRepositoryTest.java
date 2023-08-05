package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.RepositoryTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneDetailsVO;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class MilestoneRepositoryTest {

    private MilestoneRepository repository;

    @Autowired
    public MilestoneRepositoryTest(DataSource dataSource) {
        this.repository = new MilestoneRepository(dataSource);
    }

    @DisplayName("열린 마일스톤을 이름 순으로 가져온다.")
    @Test
    void testFindOpenMilestones() {
        List<MilestoneDetailsVO> actual = repository.findOpenMilestones();

        assertThat(actual.size()).isEqualTo(3);
        assertThat(actual.get(0).getName()).isEqualTo("마일스톤 0");
        assertThat(actual.get(1).getName()).isEqualTo("마일스톤 1");
        assertThat(actual.get(2).getName()).isEqualTo("마일스톤 3");
    }

    @DisplayName("모든 마일스톤 정보를 이름 순으로 가지고 온다.")
    @Test
    void testFindAllFilters() {
        List<MilestoneDetailsVO> actual = repository.findAllFilters();

        assertThat(actual.size()).isEqualTo(4);
        assertThat(actual.get(0).getName()).isEqualTo("마일스톤 0");
        assertThat(actual.get(1).getName()).isEqualTo("마일스톤 1");
        assertThat(actual.get(2).getName()).isEqualTo("마일스톤 2");
        assertThat(actual.get(3).getName()).isEqualTo("마일스톤 3");
    }
}
