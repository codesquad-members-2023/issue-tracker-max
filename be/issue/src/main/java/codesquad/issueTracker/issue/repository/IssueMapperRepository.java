package codesquad.issueTracker.issue.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import codesquad.issueTracker.issue.domain.IssueRead;
import codesquad.issueTracker.issue.domain.IssueSearch;

@Mapper
public interface IssueMapperRepository {

	List<IssueRead> findFilteredIssue(IssueSearch issueSearch);
}
