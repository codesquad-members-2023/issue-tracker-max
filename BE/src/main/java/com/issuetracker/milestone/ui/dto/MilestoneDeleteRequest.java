package com.issuetracker.milestone.ui.dto;

import com.issuetracker.common.config.exception.CustomHttpException;
import com.issuetracker.common.config.exception.ErrorType;
import com.issuetracker.milestone.application.dto.MilestoneDeleteInputData;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MilestoneDeleteRequest {

	public static MilestoneDeleteInputData toMilestoneDeleteInputData(Long id) {
		if (!verifyId(id)) {
			throw new CustomHttpException(ErrorType.ILLEGAL_ID);
		}

		return new MilestoneDeleteInputData(id);
	}

	public static boolean verifyId(Long id) {
		return id != null && id > 0;
	}
}

