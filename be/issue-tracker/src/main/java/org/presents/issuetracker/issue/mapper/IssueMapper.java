package org.presents.issuetracker.issue.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.presents.issuetracker.issue.dto.request.IssueSearchParam;
import org.presents.issuetracker.issue.entity.vo.IssueSearchCountInfo;
import org.presents.issuetracker.issue.entity.vo.IssueSearchInfo;
import org.presents.issuetracker.issue.entity.vo.IssueDetailInfo;

@Mapper
public interface IssueMapper {
	List<IssueSearchInfo> getIssues(IssueSearchParam issueSearchParam);

	IssueSearchCountInfo getIssueSearchCounts(IssueSearchParam issueSearchParam);

	IssueDetailInfo getIssueDetail(Long issueId);
}
