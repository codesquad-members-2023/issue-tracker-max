package org.presents.issuetracker.issue.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.presents.issuetracker.issue.entity.Issue;
import org.presents.issuetracker.label.entity.Label;
import org.presents.issuetracker.milestone.entity.Milestone;
import org.presents.issuetracker.user.entity.User;

@Mapper
public interface IssueMapper {
	List<Issue> getIssueList();

	User getUser(Long userId);

	List<Label> getLabelsByIssueId(Long issueId);

	Milestone getMilestoneByIssueId(Long issueId);
}
