package org.presents.issuetracker.issue.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.presents.issuetracker.issue.dto.request.IssueSearchParam;
import org.presents.issuetracker.issue.entity.vo.IssueDetailInfo;
import org.presents.issuetracker.issue.entity.vo.IssueSearchVo;

@Mapper
public interface IssueMapper {
	List<IssueSearchVo> getIssues(IssueSearchParam issueSearchParam);

	Long getLabelCount();

	Long getMilestoneCount();

	IssueDetailInfo getIssueDetail(Long issueId);
}
