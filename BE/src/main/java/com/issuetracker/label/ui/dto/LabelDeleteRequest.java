package com.issuetracker.label.ui.dto;

import com.issuetracker.common.config.exception.CustomHttpException;
import com.issuetracker.common.config.exception.ErrorType;
import com.issuetracker.label.application.dto.LabelDeleteInputData;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LabelDeleteRequest {

	public static LabelDeleteInputData toLabelDeleteInputData(Long id) {
		if (!verifyId(id)) {
			throw new CustomHttpException(ErrorType.ILLEGAL_ID);
		}

		return new LabelDeleteInputData(id);
	}

	public static boolean verifyId(Long id) {
		return id != null && id > 0;
	}
}
