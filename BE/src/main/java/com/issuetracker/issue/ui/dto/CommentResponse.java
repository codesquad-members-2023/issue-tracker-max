package com.issuetracker.issue.ui.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.issue.application.dto.CommentInformation;
import com.issuetracker.member.ui.dto.MemberResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CommentResponse {

	private Long id;
	private String content;
	private LocalDateTime createAt;
	private MemberResponse author;

	public static CommentResponse from(CommentInformation comments) {
		return new CommentResponse(
			comments.getId(),
			comments.getContent(),
			comments.getCreateAt(),
			MemberResponse.from(comments.getAuthor())
		);
	}

	public static List<CommentResponse> from(List<CommentInformation> comments) {
		return comments.stream()
			.map(CommentResponse::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
