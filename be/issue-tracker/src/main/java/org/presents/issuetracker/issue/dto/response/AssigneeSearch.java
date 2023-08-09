package org.presents.issuetracker.issue.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import org.presents.issuetracker.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AssigneeSearch {

	private final Long userId;
	private final String image;

	private static AssigneeSearch fromEntity(User assignee) {
		return AssigneeSearch.builder().userId(assignee.getUserId()).image(assignee.getImage()).build();
	}

	public static List<AssigneeSearch> from(List<User> assignees) {
		return assignees.stream().map(AssigneeSearch::fromEntity).collect(Collectors.toUnmodifiableList());
	}
}
