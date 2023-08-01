package codesquard.app.issue.repository;

import java.util.List;

import codesquard.app.issue.entity.Issue;

public interface IssueRepository {

	Long save(Issue issue);

	List<Issue> findAll();

	Issue findById(Long id);

	Long modify(Issue issue);

	Long deleteById(Long id);

	void saveIssueLabel(Long issueId, Long labelId);

	void saveIssueAssignee(Long issueId, Long userId);
}
