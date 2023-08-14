package com.issuetrackermax.domain.member;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.domain.IntegrationTestSupport;
import com.issuetrackermax.domain.member.entity.LoginType;
import com.issuetrackermax.domain.member.entity.Member;

@Transactional
class MemberRepositoryTest extends IntegrationTestSupport {

	private MemberRepository memberRepository;

	@Autowired
	public MemberRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.memberRepository = new MemberRepository(jdbcTemplate);
	}

	@DisplayName("로그인 아이디를 통해 멤버를 찾을 수 있다.")
	@Test
	void findByMemberEmail() {
		// given
		Member member = Member.builder()
			.loginId("June")
			.password("1234")
			.nickName("JOONSOO")
			.loginType(LoginType.LOCAL)
			.build();
		memberRepository.save(member);
		// when
		Member june = memberRepository.findByMemberLoginId("June");
		// then
		assertThat(june.getLoginId()).isEqualTo("June");
	}

	@Test
	void save() {
		// given
		Member member = Member.builder()
			.loginId("June")
			.password("1234")
			.nickName("JOONSOO")
			.loginType(LoginType.LOCAL)
			.build();

		// when
		Long saveId = memberRepository.save(member);

		// then
		assertThat(saveId).isNotNull();
	}
}