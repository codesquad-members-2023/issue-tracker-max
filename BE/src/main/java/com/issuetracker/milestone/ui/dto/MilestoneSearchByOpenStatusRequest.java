package com.issuetracker.milestone.ui.dto;

import javax.validation.constraints.Pattern;

import com.issuetracker.config.exception.CustomHttpException;
import com.issuetracker.config.exception.ErrorType;
import com.issuetracker.milestone.application.dto.MilestoneSearchByOpenStatusInputData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MilestoneSearchByOpenStatusRequest {

	private static final String TRUE_STRING = "true";
	private static final String FALSE_STRING = "false";
	private static final String NULL_STRING = "null";

	@Pattern(regexp = "^(true|false)$", message = "마일스톤 열림 상태는 true 또는 false 값만 입력 가능합니다.")
	private String isOpen;

	public MilestoneSearchByOpenStatusInputData toMilestoneSearchByOpenStatusInputData() {
		return new MilestoneSearchByOpenStatusInputData(
			convertFrom(isOpen)
		);
	}

	private static boolean convertFrom(String isOpenString) {
		System.out.println(isOpenString);
		if (isTrue(isOpenString)) {
			return true;
		} else if (isFalse(isOpenString)) {
			return false;
		} else if (isNull(isOpenString)) {
			return true;
		} else {
			throw new CustomHttpException(ErrorType.ILLEGAL_BOOLEAN_VALUE);
		}
	}

	private static boolean isTrue(String isOpenString) {
		return TRUE_STRING.equalsIgnoreCase(isOpenString);
	}

	private static boolean isFalse(String isOpenString) {
		return FALSE_STRING.equalsIgnoreCase(isOpenString);
	}

	private static boolean isNull(String isOpenString) {
		return NULL_STRING.equalsIgnoreCase(isOpenString);
	}
}

