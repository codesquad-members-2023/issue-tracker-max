package com.issuetrackermax.domain.member;

import java.util.List;

import org.springframework.stereotype.Component;

import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.LoginException;
import com.issuetrackermax.common.exception.domain.MemberException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MemberValidator {
	private final MemberRepository memberRepository;

	public void existByIds(List<Long> ids) {
		if (!memberRepository.existByIds(ids)) {
			throw new ApiException(MemberException.NOT_FOUND_MEMBER);
		}
	}

	public void existById(Long id) {
		if (!memberRepository.existById(id)) {
			throw new ApiException(MemberException.NOT_FOUND_MEMBER);
		}
	}

	public void existLoginId(String loginId) {
		if (memberRepository.existsLoginId(loginId)) {
			throw new ApiException(LoginException.INVALID_LOGIN_ID);
		}
	}
}
