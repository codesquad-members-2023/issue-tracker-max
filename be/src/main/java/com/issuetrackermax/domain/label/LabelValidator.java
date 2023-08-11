package com.issuetrackermax.domain.label;

import java.util.List;

import org.springframework.stereotype.Component;

import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.LabelException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class LabelValidator {
	private final LabelRepository labelRepository;

	public void existByTitle(String title) {
		if (labelRepository.existByTitle(title)) {
			throw new ApiException(LabelException.INVALID_LABEL_TITLE);
		}
	}

	public void existById(Long id) {
		if (!labelRepository.existById(id)) {
			throw new ApiException(LabelException.NOT_FOUND_LABEL);
		}
	}

	public void existByIds(List<Long> ids) {
		if (!labelRepository.existByIds(ids)) {
			throw new ApiException(LabelException.NOT_FOUND_LABEL);
		}
	}
}
