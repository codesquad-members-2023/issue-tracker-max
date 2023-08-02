package org.presents.issuetracker.issue.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.presents.issuetracker.issue.dto.vo.IssueVo;

@Mapper
public interface IssueMapper {
	List<IssueVo> getIssues();

	// User getUser(Long userId);
	//
	// List<Label> getLabelsByIssueId(Long issueId);
	//
	// Milestone getMilestoneByIssueId(Long issueId);

}
