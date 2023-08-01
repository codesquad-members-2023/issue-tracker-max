package com.issuetracker.member.application;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.issuetracker.config.exception.MemberNotFoundException;
import com.issuetracker.member.domain.Member;
import com.issuetracker.member.infrastructure.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberValidator {

	private final MemberRepository memberRepository;

	public Member getVerifiedMember(Long memberId) {
		if(!memberRepository.existById(memberId)) {
			throw new MemberNotFoundException();
		}

		return Member.createInstanceById(memberId);
	}

	public List<Member> getVerifiedMembers(List<Long> memberIds) {
		if (memberIds.isEmpty()) {
			return Collections.emptyList();
		}

		memberIds = getNonNullLabels(memberIds);

		if(!memberRepository.existByIds(memberIds)) {
			throw new MemberNotFoundException();
		}

		return memberIds.stream()
			.map(Member::createInstanceById)
			.collect(Collectors.toUnmodifiableList());
	}

	private List<Long> getNonNullLabels(List<Long> ids) {
		return ids.stream()
			.filter(Objects::nonNull)
			.collect(Collectors.toUnmodifiableList());
	}
}


