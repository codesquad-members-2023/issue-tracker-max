package com.issuetrackermax.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.common.exception.InvalidLoginIdException;
import com.issuetrackermax.common.exception.InvalidPasswordException;
import com.issuetrackermax.controller.auth.dto.response.MemberProfileResponse;
import com.issuetrackermax.controller.member.dto.request.SignUpRequest;
import com.issuetrackermax.domain.member.MemberRepository;
import com.issuetrackermax.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepository memberRepository;

	@Transactional
	public void registerMember(SignUpRequest signUpRequest) {
		if (signUpRequest.getPassword().length() < 8) {
			throw new InvalidPasswordException();
		}
		if (memberRepository.existsLoginId(signUpRequest.getLoginId())) {
			throw new InvalidLoginIdException();
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
