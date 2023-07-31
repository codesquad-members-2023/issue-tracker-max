package org.presents.issuetracker.issue.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.presents.issuetracker.issue.dto.IssueDto;

@Mapper
public interface IssueMapper {
	List<IssueDto> getIssueList();
}
