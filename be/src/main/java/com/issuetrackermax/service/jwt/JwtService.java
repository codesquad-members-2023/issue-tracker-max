package com.issuetrackermax.service.jwt;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.common.exception.InCorrectPasswordException;
import com.issuetrackermax.domain.jwt.JwtRepository;
import com.issuetrackermax.domain.jwt.entity.Jwt;
import com.issuetrackermax.domain.member.MemberRepository;
import com.issuetrackermax.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtService {
	private final JwtRepository jwtRepository;
	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;

	@Transactional
	public Jwt login(String email, String password) throws Exception {
		Member member = memberRepository.findByMemberLoginId(email).get();

		if (!verifyPassword(member, password)) {
			throw new InCorrectPasswordException();
		}
		Jwt jwt = jwtProvider.createJwt(generateMemberClaims(member.getId()));

		jwtRepository.saveRefreshToken(jwt.getRefreshToken(), member.getId());

		return jwt;
	}

	@Transactional
	public Jwt reissueAccessToken(String refreshToken) {
		jwtProvider.getClaims(refreshToken);
		Long memberId = jwtRepository.findByRefreshToken(refreshToken);

		return jwtProvider.reissueAccessToken(generateMemberClaims(memberId), refreshToken);
	}

	private Map<String, Object> generateMemberClaims(Long memberId) {
		return Map.of(
			"memberId", memberId
		);
	}

	private boolean existMember(Member member) {
		return member != null;
	}

	private boolean verifyPassword(Member member, String password) {
		return existMember(member) && member.getPassword().equals(password);
	}
}
