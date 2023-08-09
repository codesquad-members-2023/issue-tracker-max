package com.issuetracker.issue.ui.dto.comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.issue.application.dto.comment.IssueCommentInformation;
import com.issuetracker.member.ui.dto.MemberResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class IssueCommentResponse {

	private Long id;
	private String content;
	private LocalDateTime createAt;
	private MemberResponse author;

	public static IssueCommentResponse from(IssueCommentInformation comments) {
		return new IssueCommentResponse(
			comments.getId(),
			comments.getContent(),
			comments.getCreateAt(),
			MemberResponse.from(comments.getAuthor())
		);
	}

	public static List<IssueCommentResponse> from(List<IssueCommentInformation> comments) {
		return comments.stream()
			.map(IssueCommentResponse::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
