package com.issuetracker.label.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.config.exception.CustomHttpException;
import com.issuetracker.config.exception.ErrorType;
import com.issuetracker.label.application.dto.LabelCreateInformation;
import com.issuetracker.label.application.dto.LabelCreateInputData;
import com.issuetracker.label.application.dto.LabelUpdateInputData;
import com.issuetracker.label.domain.LabelRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LabelService {

	private final LabelRepository labelRepository;

	@Transactional
	public LabelCreateInformation create(LabelCreateInputData labelCreateInputData) {
		return LabelCreateInformation.from(
			labelRepository.save(labelCreateInputData.toLabelForCreate()));
	}

	@Transactional
	public void update(LabelUpdateInputData labelUpdateInputData) {
		int numberOfUpdatedRow = labelRepository.update(labelUpdateInputData.toLabelForUpdate());

		if (numberOfUpdatedRow == 0) {
			throw new CustomHttpException(ErrorType.LABEL_NOT_FOUND);
		}
	}
}
