package com.issuetracker.unit.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.issuetracker.member.infrastructure.MemberRepository;
import com.issuetracker.util.DatabaseInitialization;
import com.issuetracker.util.RepositoryTest;

@RepositoryTest
class MemberRepositoryTest {

	private MemberRepository memberRepository;
	private DatabaseInitialization databaseInitialization;

	@Autowired
	public MemberRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.memberRepository = new MemberRepository(jdbcTemplate);
		this.databaseInitialization = new DatabaseInitialization(jdbcTemplate);
	}

	@BeforeEach
	void setUp() {
		databaseInitialization.initialization();
		databaseInitialization.loadData();
	}

	@Test
	void 멤버_아이디를_입력하면_존재하는_멤버인지_확일할_수_있다() {
		// given
		Long id = 1L;

		// when
		boolean result = memberRepository.existById(id);

		// then
		assertThat(result).isTrue();
	}

	@Test
	void 멤버_아이디_목록을_입력하면_존재하는_멤버인지_확일할_수_있다() {
		// given
		List<Long> memberIds = List.of(1L, 2L, 3L, 4L);

		// when
		boolean result = memberRepository.existByIds(memberIds);

		// then
		assertThat(result).isTrue();
	}
}
