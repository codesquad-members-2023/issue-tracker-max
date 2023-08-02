package com.issuetracker.member.application;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.issuetracker.config.exception.MemberNotFoundException;
import com.issuetracker.member.infrastructure.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberValidator {

	private final MemberRepository memberRepository;

	public void verifyMember(Long id) {
		if(Objects.nonNull(id) && !memberRepository.existById(id)) {
			throw new MemberNotFoundException();
		}
	}

	public void verifyMembers(List<Long> ids) {
		if (Objects.isNull(ids) || ids.isEmpty()) {
			return;
		}

		ids = getNonNullLabels(ids);

		if(!memberRepository.existByIds(ids)) {
			throw new MemberNotFoundException();
		}
	}

	private List<Long> getNonNullLabels(List<Long> ids) {
		return ids.stream()
			.filter(Objects::nonNull)
			.collect(Collectors.toUnmodifiableList());
	}
}


