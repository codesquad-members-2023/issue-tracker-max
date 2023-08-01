package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.RepositoryTest;
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
}
