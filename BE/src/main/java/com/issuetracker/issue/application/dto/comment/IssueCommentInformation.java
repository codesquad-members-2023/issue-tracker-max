package com.issuetracker.issue.application.dto.comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.issue.domain.comment.IssueCommentRead;
import com.issuetracker.member.application.dto.MemberInformation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IssueCommentInformation {

	private Long id;
	private String content;
	private LocalDateTime createAt;
	private MemberInformation author;

	public static IssueCommentInformation from(IssueCommentRead issueCommentRead) {
		return new IssueCommentInformation(
			issueCommentRead.getId(),
			issueCommentRead.getContent(),
			issueCommentRead.getCreateAt(),
			MemberInformation.from(issueCommentRead.getAuthor())
		);
	}

	public static List<IssueCommentInformation> from(List<IssueCommentRead> comments) {
		return comments.stream()
			.map(IssueCommentInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
