package com.issuetracker.issue.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.issue.domain.IssueCommentRead;
import com.issuetracker.member.application.dto.MemberInformation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentInformation {

	private Long id;
	private String content;
	private LocalDateTime createAt;
	private MemberInformation author;

	public static CommentInformation from(IssueCommentRead issueCommentRead) {
		return new CommentInformation(
			issueCommentRead.getId(),
			issueCommentRead.getContent(),
			issueCommentRead.getCreateAt(),
			MemberInformation.from(issueCommentRead.getAuthor())
		);
	}

	public static List<CommentInformation> from(List<IssueCommentRead> comments) {
		return comments.stream()
			.map(CommentInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
