package com.issuetracker.issue.ui.dto;

import java.util.List;

import com.issuetracker.member.application.dto.MemberInformation;
import com.issuetracker.member.ui.dto.MemberResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AuthorResponses {

	private List<MemberResponse> authors;

	public static AuthorResponses from(List<MemberInformation> memberInformation) {
		return new AuthorResponses(MemberResponse.from(memberInformation));
	}
}
