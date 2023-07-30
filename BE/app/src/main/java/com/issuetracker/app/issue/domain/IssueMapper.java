package com.issuetracker.app.issue.domain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.issuetracker.app.issue.application.dto.IssueSearchData;

@Mapper
public interface IssueMapper {

	List<Issue> search(IssueSearchData issueSearchData);
}
