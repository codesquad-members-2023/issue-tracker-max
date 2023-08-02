package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.RepositoryTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
public class LabelRepositoryTest {

    private LabelRepository repository;

    @Autowired
    public LabelRepositoryTest(DataSource dataSource) {
        this.repository = new LabelRepository(dataSource);
    }

    @DisplayName("이슈 아이디로 해당 이슈의 모든 label 정보를 불러온다.")
    @Test
    void findAllByIssueIdTest() {
        List<LabelVO> actual = repository.findAllByIssueId(3L);

        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(0).getName()).isEqualTo("라벨 1");
        assertThat(actual.get(0).getBackgroundColor()).isEqualTo("#F08080");
        assertThat(actual.get(1).getTextColor()).isEqualTo("#000000");
    }

    @DisplayName("모든 라벨을 찾아 이름 순으로 반환한다.")
    @Test
    void testFindAll() {

        List<LabelDetailsVO> actual = repository.findAll();
        assertThat(actual.size()).isEqualTo(4);
        assertThat(actual.get(0).getName()).isEqualTo("라벨 0");
        assertThat(actual.get(1).getName()).isEqualTo("라벨 1");
        assertThat(actual.get(2).getName()).isEqualTo("라벨 2");
        assertThat(actual.get(3).getName()).isEqualTo("라벨 3");

    }
}
