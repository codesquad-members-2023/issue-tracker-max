package com.issuetracker.label.application;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.issuetracker.config.exception.LabelNotFoundException;
import com.issuetracker.label.domain.Label;
import com.issuetracker.label.infrastructure.LabelRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LabelValidator {

	private final LabelRepository labelRepository;

	public List<Label> getVerifyLabels(List<Long> ids) {
		if (ids.isEmpty()) {
			return Collections.emptyList();
		}

		ids = getNonNullLabels(ids);

		if (!labelRepository.existByIds(ids)) {
			throw new LabelNotFoundException();
		}

		return ids.stream()
			.map(Label::createInstanceById)
			.collect(Collectors.toUnmodifiableList());
	}

	private List<Long> getNonNullLabels(List<Long> ids) {
		return ids.stream()
			.filter(Objects::nonNull)
			.collect(Collectors.toUnmodifiableList());
	}
}
