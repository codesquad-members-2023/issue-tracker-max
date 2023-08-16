package com.issuetracker.unit.infrastrucure;

import static com.issuetracker.util.fixture.MemberFixture.MEMBER1;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.issuetracker.member.domain.Member;
import com.issuetracker.member.domain.MemberRepository;
import com.issuetracker.member.infrastructure.JdbcMemberRepository;
import com.issuetracker.util.JdbcRepositoryTest;

class MemberJdbcRepositoryTest extends JdbcRepositoryTest {

	private MemberRepository memberRepository;

	@Autowired
	public MemberJdbcRepositoryTest(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
		this.memberRepository = new JdbcMemberRepository(jdbcTemplate);
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

	@Test
	void 회원을_조회한다() {
		// when
		Member actual = memberRepository.findById(MEMBER1.getId()).orElseThrow();

		// then
		Assertions.assertAll(
			() -> assertThat(actual.getId()).isEqualTo(MEMBER1.getId()),
			() -> assertThat(actual.getNickname()).isEqualTo(MEMBER1.getNickname()),
			() -> assertThat(actual.getProfileImageUrl()).isEqualTo(MEMBER1.getProFileImageUrl())
		);
	}

	@Test
	void 회원을_수정하다() {
		// given
		Member member = Member.builder()
			.id(MEMBER1.getId())
			.nickname("mandu")
			.password("test")
			.profileImageUrl("http://image.com")
			.build();

		// when
		int actual = memberRepository.update(member);

		// then
		assertThat(actual).isEqualTo(1);
	}
}

