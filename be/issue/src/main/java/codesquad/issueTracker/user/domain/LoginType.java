package codesquad.issueTracker.user.domain;

import lombok.Getter;

@Getter
public enum LoginType {

	LOCAL("LOCAL"),
	GITHUB("GITHUB");

	private String type;

	LoginType(String type) {
		this.type = type;
	}
}
