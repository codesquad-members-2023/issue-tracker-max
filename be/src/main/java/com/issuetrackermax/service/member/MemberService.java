package com.issuetrackermax.service.member;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.controller.auth.dto.response.MemberProfileResponse;
import com.issuetrackermax.controller.member.dto.request.SignUpRequest;
import com.issuetrackermax.controller.member.dto.response.MemberContentResponse;
import com.issuetrackermax.domain.member.MemberRepository;
import com.issuetrackermax.domain.member.MemberValidator;
import com.issuetrackermax.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberValidator memberValidator;
	private final MemberRepository memberRepository;

	public void checkLoginIdDuplication(String loginId) {
		memberValidator.existLoginId(loginId);
	}

	@Transactional(readOnly = true)
	public List<MemberContentResponse> getMemberList() {
		return MemberContentResponse.from(memberRepository.findAll());
	}

	@Transactional
	public void registerMember(SignUpRequest signUpRequest) {
		memberValidator.existLoginId(signUpRequest.getLoginId());
		memberRepository.save(signUpRequest.toMember());
	}

	@Transactional
	public Member registerOauthMember(MemberProfileResponse memberProfileResponse) {
		if (!memberRepository.existsLoginId(memberProfileResponse.getLoginId())) {
			memberRepository.save(memberProfileResponse.toMember());
		}
		return memberRepository.findByMemberLoginId(memberProfileResponse.getLoginId());
	}
}
