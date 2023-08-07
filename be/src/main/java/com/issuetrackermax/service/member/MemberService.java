package com.issuetrackermax.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.LoginException;
import com.issuetrackermax.controller.auth.dto.response.MemberProfileResponse;
import com.issuetrackermax.controller.member.dto.request.SignUpRequest;
import com.issuetrackermax.domain.member.MemberRepository;
import com.issuetrackermax.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	private static final int PASSWORD_MIN_LENGTH = 8;
	private final MemberRepository memberRepository;

	@Transactional
	public void registerMember(SignUpRequest signUpRequest) {
		if (memberRepository.existsLoginId(signUpRequest.getLoginId())) {
			throw new ApiException(LoginException.INVALID_LOGIN_ID);
		}
		memberRepository.save(signUpRequest.toMember());
	}

	@Transactional
	public Member registerOauthMember(MemberProfileResponse memberProfileResponse) {
		if (memberRepository.findByMemberLoginId(memberProfileResponse.getLoginId()).isEmpty()) {
			memberRepository.save(memberProfileResponse.toMember());
		}
		return memberRepository.findByMemberLoginId(memberProfileResponse.getLoginId()).get();
	}
}
