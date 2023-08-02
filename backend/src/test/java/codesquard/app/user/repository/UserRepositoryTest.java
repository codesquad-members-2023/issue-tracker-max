package codesquard.app.user.repository;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.user.entity.User;

class UserRepositoryTest extends IntegrationTestSupport {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp() {
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0");
		jdbcTemplate.update("TRUNCATE TABLE comment");
		jdbcTemplate.update("TRUNCATE TABLE issue");
		jdbcTemplate.update("TRUNCATE TABLE milestone");
		jdbcTemplate.update("TRUNCATE TABLE user");
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
	}

	@Transactional
	@DisplayName("회원 객체가 주어지고 회원 저장을 요청할때 새로운 회원이 등록된다")
	@Test
	void save() {
		// given
		User user = new User(null, "user1", "user1@gmail.com", "user1user1@", null);
		// when
		Long savedId = userRepository.save(user);
		// then
		User findUser = userRepository.findById(savedId);
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(findUser).isNotNull();
			softAssertions.assertAll();
		});
	}
}
