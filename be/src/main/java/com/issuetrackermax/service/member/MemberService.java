package com.issuetrackermax.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetrackermax.controller.member.dto.request.SignUpRequest;
import com.issuetrackermax.domain.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepository memberRepository;

	@Transactional
	public void registerMember(SignUpRequest signUpRequest) {
		/*
		 * TODO
		 */
		// if(memberRepository.existEmail(memberRegisterDto.getEmail())){
		// 	return;
		// }
		memberRepository.save(signUpRequest.toEntity());
	}
}
