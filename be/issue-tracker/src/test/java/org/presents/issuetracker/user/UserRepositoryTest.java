package org.presents.issuetracker.user;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.presents.issuetracker.annotation.RepositoryTest;
import org.presents.issuetracker.user.entity.User;
import org.presents.issuetracker.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@RepositoryTest
public class UserRepositoryTest {

	private final UserRepository userRepository;

	@Autowired
	public UserRepositoryTest(NamedParameterJdbcTemplate jdbcTemplate) {
		userRepository = new UserRepository(jdbcTemplate);
	}

	@Test
	@DisplayName("이슈 아이디를 입력받아 이슈에 담당된 사용자의 목록을 조회한다.")
	public void findByIssueId() {
		//given
		Long issueId = 1L;

		//when
		List<User> users = userRepository.findByIssueId(issueId);

		//then
		int expectedUserCount = 2;
		assertThat(users.size()).isEqualTo(expectedUserCount);
	}

	@Test
	@DisplayName("사용자의 목록을 조회한다.")
	public void findAll() {
		//when
		List<User> users = userRepository.findAll();

		//then
		int expectedUserCount = 6;
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(users).hasSize(expectedUserCount);
			for (User user : users) {
				softAssertions.assertThat(user.getUserId()).isNotNull();
				softAssertions.assertThat(user.getLoginId()).isNotNull();
				softAssertions.assertThat(user.getImage()).isNotNull();
				softAssertions.assertThat(user.getPassword()).isNull();
			}
		});
	}
}
