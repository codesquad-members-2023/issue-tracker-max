package codesquad.issueTracker.user.repository;

import codesquad.issueTracker.user.domain.LoginType;
import codesquad.issueTracker.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@JdbcTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    private UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.userRepository = new UserRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("회원가입 테스트")
    void insert() {
        // given
        User user = User.builder().
                email("aaa@ncd.com").
                password("12345678").
                name("감귤감귤감귬감귤").
                loginType(LoginType.LOCAL).build();
        // when
        Long id = userRepository.insert(user);

        // then
        assertThat(id).isEqualTo(1L);


    }
}