package com.issuetrackermax.domain.jwt;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.domain.IntegrationTestSupport;

@Transactional
class JwtRepositoryTest extends IntegrationTestSupport {

	private JwtRepository jwtRepository;

	@Autowired
	public JwtRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.jwtRepository = new JwtRepository(jdbcTemplate);
	}

	@DisplayName("RefreshToken을 통해 멤버 아이디를 찾는다.")
	@Test
	void findByRefreshToken() {
		// given
		String token = "tokentoken";
		Long memberId = 1L;
		jwtRepository.saveRefreshToken(token, memberId);

		// when
		Long savedemberId = jwtRepository.findByRefreshToken(token);

		// then
		assertThat(savedemberId).isEqualTo(memberId);
	}

	@DisplayName("RefreshToken을 저장한다.")
	@Test
	void saveRefreshToken() {
		// given
		String token = "tokentoken";
		Long memberId = 1L;

		// when
		Long savedRefreshTokenId = jwtRepository.saveRefreshToken(token, memberId);

		// then
		assertThat(savedRefreshTokenId).isEqualTo(1L);
	}

}