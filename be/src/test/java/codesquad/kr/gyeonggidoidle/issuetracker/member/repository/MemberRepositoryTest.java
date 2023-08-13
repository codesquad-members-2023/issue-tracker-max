package codesquad.kr.gyeonggidoidle.issuetracker.member.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.RepositoryTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.Member;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.MemberRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.vo.MemberDetailsVO;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@RepositoryTest
class MemberRepositoryTest {

    private MemberRepository repository;

    @Autowired
    public MemberRepositoryTest(NamedParameterJdbcTemplate template) {
        this.repository = new MemberRepository(template);
    }

    @DisplayName("이슈아이디로 해당 이슈의 모든 할당자의 프로필을 가지고 온다.")
    @Test
    void findAllProfilesByIssueId() {
        //when
        List<String> actual = repository.findAllProfilesByIssueId(1L);

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual).hasSize(2);
            assertions.assertThat(actual.get(0)).isEqualTo("https://e7.pngegg.com/pngimages/981/645/png-clipart-default-profile-united-states-computer-icons-desktop-free-high-quality-person-icon-miscellaneous-silhouette.png");
            assertions.assertThat(actual.get(1)).isEqualTo("https://e7.pngegg.com/pngimages/981/645/png-clipart-default-profile-united-states-computer-icons-desktop-free-high-quality-person-icon-miscellaneous-silhouette.png");
        });
    }

    @DisplayName("모든 가입자의 정보를 이름 순으로 가지고 온다.")
    @Test
    void findAllFilters() {
        //when
        List<MemberDetailsVO> actual = repository.findAllFilters();

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual).hasSize(3);
            assertions.assertThat(actual.get(0).getName()).isEqualTo("ati");
            assertions.assertThat(actual.get(1).getName()).isEqualTo("joy");
            assertions.assertThat(actual.get(2).getName()).isEqualTo("nag");
        });
    }

    @DisplayName("이메일로 해당 멤버를 찾는다.")
    @Test
    void findByEmail() {
        // when
        Member actual = repository.findByEmail("joy@codesquad.kr");

        // then
        assertSoftly(assertions -> {
            assertions.assertThat(actual.getId()).isEqualTo(2);
            assertions.assertThat(actual.getName()).isEqualTo("joy");
            assertions.assertThat(actual.getPassword()).isEqualTo("1q2w3e4r!");
        });
    }
}
