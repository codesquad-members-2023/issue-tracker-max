package codesquad.issueTracker.user.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import annotation.RepositoryTest;
import codesquad.issueTracker.jwt.domain.Token;
import codesquad.issueTracker.user.domain.LoginType;
import codesquad.issueTracker.user.domain.User;

@RepositoryTest
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

	@Test
	@DisplayName("유저 찾기 테스트")
	void findByEmail() {
		// given
		User user = User.builder().
			email("aaa@ncd.com").
			password("12345678").
			name("감귤감귤감귬감귤").
			loginType(LoginType.LOCAL).build();
		Long id = userRepository.insert(user);
		// when
		User result = userRepository.findByEmail(user.getEmail()).get();
		// then
		System.out.println(id);
		assertThat(id).isEqualTo(1L);
		assertThat(result.getEmail()).isEqualTo(user.getEmail());
		assertThat(result.getName()).isEqualTo(user.getName());
	}

	@Test
	@DisplayName("리프레시 토큰 저장 메서드 ")
	void insertToken() {
		// given
		Token token = Token.builder()
			.userId(1L)
			.refreshToken("asdf.fff.ggg")
			.build();
		// when
		Long id = userRepository.insertRefreshToken(token.getUserId(), token.getRefreshToken());

		// then
		assertThat(id).isEqualTo(1L);
	}

	@Test
	@DisplayName("유저 id 로 리프레시 토큰찾기")
	void findTokenByUserId() {
		// given
		Token token = Token.builder()
			.userId(1L)
			.refreshToken("asdf.fff.ggg")
			.build();

		Long id = userRepository.insertRefreshToken(token.getUserId(), token.getRefreshToken());
		// when
		Token result = userRepository.findTokenByUserId(1L).get();
		// then
		System.out.println(id);
		assertThat(id).isEqualTo(1L);
		assertThat(result.getRefreshToken()).isEqualTo(token.getRefreshToken());
	}

	@DisplayName("회원 id에 해당하는 refreshToken 을 업데이트 한다.")
	@Test
	void updateRefreshToken() {

		// given
		User user = User.builder()
			.email("aaa@ncd.com")
			.password("12345678")
			.name("감귤감귤감귬감귤")
			.loginType(LoginType.LOCAL).build();

		Long userId = userRepository.insert(user);

		Token token = Token.builder()
			.userId(1L)
			.refreshToken("asdf.fff.ggg")
			.build();

		userRepository.insertRefreshToken(userId, token.getRefreshToken());

		// when
		userRepository.updateRefreshToken(1L, "aaa.bbb.ccc");
		Token result = userRepository.findTokenByUserId(1L).get();

		// then
		assertThat(result).extracting("id", "userId", "refreshToken")
			.containsExactly(1L, 1L, "aaa.bbb.ccc");
	}

}