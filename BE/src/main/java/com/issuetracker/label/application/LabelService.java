package com.issuetracker.label.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.common.config.exception.CustomHttpException;
import com.issuetracker.common.config.exception.ErrorType;
import com.issuetracker.label.application.dto.LabelCountMetadataInformation;
import com.issuetracker.label.application.dto.LabelCreateInformation;
import com.issuetracker.label.application.dto.LabelCreateInputData;
import com.issuetracker.label.application.dto.LabelDeleteInputData;
import com.issuetracker.label.application.dto.LabelInformation;
import com.issuetracker.label.application.dto.LabelUpdateInputData;
import com.issuetracker.label.application.dto.LabelsInformation;
import com.issuetracker.label.domain.LabelRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LabelService {

	private final LabelRepository labelRepository;
	private final LabelValidator labelValidator;

	@Transactional
	public LabelCreateInformation create(LabelCreateInputData labelCreateInputData) {
		labelValidator.verifyDuplicationTitle(labelCreateInputData.getTitle());
		return LabelCreateInformation.from(
			labelRepository.save(labelCreateInputData.toLabelForCreate()));
	}

	@Transactional
	public void update(LabelUpdateInputData labelUpdateInputData) {
		int numberOfUpdatedRow = labelRepository.update(labelUpdateInputData.toLabelForUpdate());
		labelValidator.verifyUpdatedOrDeletedCount(numberOfUpdatedRow);
	}

	@Transactional
	public void delete(LabelDeleteInputData labelDeleteInputData) {
		int numberOfDeleteRow = labelRepository.delete(labelDeleteInputData.toLabelForDelete());
		labelValidator.verifyUpdatedOrDeletedCount(numberOfDeleteRow);
	}

	public LabelsInformation search() {

		return LabelsInformation.from(
			LabelCountMetadataInformation.from(labelRepository.calculateMetadata()),
			LabelInformation.from(labelRepository.findAll())
		);
	}
}
