package com.issuetracker.issue.domain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IssueMapper {

	List<IssueRead> search(IssueSearch issueSearch);

	IssueDetailRead findById(Long id);
}
