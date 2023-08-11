package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.RepositoryTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.Milestone;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneDetailsVO;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

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

    @DisplayName("라벨 내용을 수정하고 성공하면 true를 반환한다.")
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

    @DisplayName("마일스톤 아이디를 받아 is_deleted를 true로 바꾸고 성공하면 true를 반환한다")
    @Test
    void delete() {
        // when
        boolean actual = repository.delete(2L);
        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("마일스톤 아이디를 받아 is_open을 바꾸고 성공하면 true를 반환한다")
    @Test
    void updateStatus() {
        // when
        boolean actual = repository.updateStatus(1L, false);
        // then
        assertThat(actual).isTrue();
    }
}
