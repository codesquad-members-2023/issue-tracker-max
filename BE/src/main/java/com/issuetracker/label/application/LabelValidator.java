package com.issuetracker.label.application;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.issuetracker.config.exception.LabelNotFoundException;
import com.issuetracker.label.infrastructure.LabelRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LabelValidator {

	private final LabelRepository labelRepository;

	public void verifyLabels(List<Long> ids) {
		if (Objects.isNull(ids) || ids.isEmpty()) {
			return;
		}

		ids = getNonNullLabels(ids);

		if (!labelRepository.existByIds(ids)) {
			throw new LabelNotFoundException();
		}
	}

	private List<Long> getNonNullLabels(List<Long> ids) {
		return ids.stream()
			.filter(Objects::nonNull)
			.collect(Collectors.toUnmodifiableList());
	}
}
