package com.issuetrackermax.service.Jwt;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.domain.jwt.JwtRepository;
import com.issuetrackermax.domain.jwt.entity.Jwt;
import com.issuetrackermax.domain.jwt.service.JwtProvider;
import com.issuetrackermax.domain.member.Entity.Member;
import com.issuetrackermax.domain.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtService {
	private final JwtRepository jwtRepository;
	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;

	public Jwt login(String email, String password) throws Exception {
		Member member = memberRepository.findByMemberEmail(email).get();

		if (!verifyPassword(member, password)) {
			throw new Exception();
		}

		Jwt jwt = jwtProvider.createJwt(generateMemberClaims(member.getId()));

		jwtRepository.saveRefreshToken(jwt.getRefreshToken(), member.getId());

		return jwt;
	}

	@Transactional
	public Jwt reissueAccessToken(String refreshToken) {
		jwtProvider.getClaims(refreshToken);
		Long memberId = jwtRepository.findByRefreshToken(refreshToken);
		/*
		 * TODO
		 * */
		// if(memberId == null) {
		// 	throw new IllegalJwtTokenException(JwtTokenType.REFRESH);
		// }

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
