package codesquad.kr.gyeonggidoidle.issuetracker.member.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.RepositoryTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
public class MemberRepositoryTest {

    private MemberRepository repository;

    @Autowired
    public MemberRepositoryTest(DataSource dataSource) {
        this.repository = new MemberRepository(dataSource);
    }

    @DisplayName("이슈아이디로 해당 이슈의 모든 할당자의 프로필을 가지고 온다.")
    @Test
    void findAllProfilesByIssueIdTest() {
        List<String> actual = repository.findAllProfilesByIssueId(1L);

        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(0)).isEqualTo("https://e7.pngegg.com/pngimages/981/645/png-clipart-default-profile-united-states-computer-icons-desktop-free-high-quality-person-icon-miscellaneous-silhouette.png");
        assertThat(actual.get(1)).isEqualTo("https://e7.pngegg.com/pngimages/981/645/png-clipart-default-profile-united-states-computer-icons-desktop-free-high-quality-person-icon-miscellaneous-silhouette.png");
    }
}
