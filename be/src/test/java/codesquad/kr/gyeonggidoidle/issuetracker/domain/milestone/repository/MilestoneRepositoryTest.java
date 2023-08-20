package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.RepositoryTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.Milestone;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDate;
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
        List<MilestoneDetailsVO> actual = repository.getMilestoneFilter();

        //then
        assertSoftly(assertions -> {
            assertThat(actual).hasSize(4);
            assertThat(actual.get(0).getName()).isEqualTo("마일스톤 0");
            assertThat(actual.get(1).getName()).isEqualTo("마일스톤 1");
            assertThat(actual.get(2).getName()).isEqualTo("마일스톤 2");
            assertThat(actual.get(3).getName()).isEqualTo("마일스톤 3");
        });
    }

    @DisplayName("Label을 받아서 db에 저장한다.")
    @Test
    void save() {
        // given
        Milestone milestone = Milestone.builder()
                .name("name")
                .description("descrip")
                .dueDate(LocalDate.now())
                .build();
        // when
        boolean actual = repository.save(milestone);
        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("라벨 내용 수정을 성공한다.")
    @Test
    void update() {
        // given
        Milestone milestone = Milestone.builder()
                .id(1L)
                .name("update title")
                .description("tmp")
                .dueDate(LocalDate.now())
                .build();
        // when
        boolean actual = repository.update(milestone);
        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("마일스톤 삭제에 성공한다.")
    @Test
    void delete() {
        // when
        boolean actual = repository.delete(2L);
        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("마일스톤 상태변경을 성공한다.")
    @Test
    void updateStatus() {
        // when
        boolean actual = repository.updateStatus(1L, false);
        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("issueId로 issueId의 MileStone을 가져올 수 있다.")
    @Test
    void getMilestoneByIssueId() {
        //given
        Long issueId = 1L;

        //when
        MilestoneVO actual = repository.getMilestoneByIssueId(issueId);

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual.getName()).isEqualTo("마일스톤 1");
            assertions.assertThat(actual.getOpenIssueCount()).isEqualTo(1);
            assertions.assertThat(actual.getClosedIssueCount()).isEqualTo(2);
        });
    }
}
