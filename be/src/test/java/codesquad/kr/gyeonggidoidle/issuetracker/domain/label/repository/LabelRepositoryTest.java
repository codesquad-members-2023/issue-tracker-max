package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.RepositoryTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelVO;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@RepositoryTest
class LabelRepositoryTest {

    private LabelRepository repository;

    @Autowired
    public LabelRepositoryTest(NamedParameterJdbcTemplate template) {
        this.repository = new LabelRepository(template);
    }

    @DisplayName("이슈 아이디로 해당 이슈의 모든 label 정보를 불러온다.")
    @Test
    void findAllByIssueId() {
        //when
        List<LabelVO> actual = repository.findAllByIssueId(3L);

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual).hasSize(2);
            assertions.assertThat(actual.get(0).getName()).isEqualTo("라벨 1");
            assertions.assertThat(actual.get(0).getBackgroundColor()).isEqualTo("#F08080");
            assertions.assertThat(actual.get(1).getTextColor()).isEqualTo("#000000");
        });
    }

    @DisplayName("모든 라벨을 찾아 이름 순으로 반환한다.")
    @Test
    void findAll() {
        //when
        List<LabelDetailsVO> actual = repository.findAll();

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual).hasSize(4);
            assertions.assertThat(actual.get(0).getName()).isEqualTo("라벨 0");
            assertions.assertThat(actual.get(1).getName()).isEqualTo("라벨 1");
            assertions.assertThat(actual.get(3).getName()).isEqualTo("라벨 3");
        });
    }
}
