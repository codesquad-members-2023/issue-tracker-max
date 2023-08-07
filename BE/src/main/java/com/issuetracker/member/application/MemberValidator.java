package com.issuetracker.member.application;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.issuetracker.config.exception.CustomHttpException;
import com.issuetracker.config.exception.ErrorType;
import com.issuetracker.member.domain.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberValidator {

	private final MemberRepository memberRepository;

	public void verifyMember(Long id) {
		if(Objects.nonNull(id) && !memberRepository.existById(id)) {
			throw new CustomHttpException(ErrorType.MEMBER_NOT_FOUND);
		}
	}

	public void verifyMembers(List<Long> ids) {
		if (Objects.isNull(ids) || ids.isEmpty()) {
			return;
		}

		if(!memberRepository.existByIds(ids)) {
			throw new CustomHttpException(ErrorType.MEMBER_NOT_FOUND);
		}
	}
}


