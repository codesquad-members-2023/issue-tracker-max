package com.issuetracker.label.application;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.issuetracker.config.exception.CustomHttpException;
import com.issuetracker.config.exception.ErrorType;
import com.issuetracker.label.domain.LabelRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LabelValidator {

	private final LabelRepository labelRepository;

	public void verifyLabel(Long id) {
		if (Objects.isNull(id) || !labelRepository.existById(id)) {
			throw new CustomHttpException(ErrorType.LABEL_NOT_FOUND);
		}
	}

	public void verifyLabels(List<Long> ids) {
		if (Objects.isNull(ids) || ids.isEmpty()) {
			return;
		}

		if (!labelRepository.existByIds(ids)) {
			throw new CustomHttpException(ErrorType.LABEL_NOT_FOUND);
		}
	}
}
