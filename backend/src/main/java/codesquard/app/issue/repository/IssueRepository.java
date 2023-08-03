package codesquard.app.issue.repository;

import java.util.List;

import codesquard.app.issue.entity.Issue;
import codesquard.app.label.entity.Label;
import codesquard.app.user.entity.User;

public interface IssueRepository {

	Long save(Issue issue);

	List<Issue> findAll();

	Issue findBy(Long id);

	Long modify(Issue issue);

	void deleteBy(Long id);

	void saveIssueLabel(Long issueId, Long labelId);

	void saveIssueAssignee(Long issueId, Long userId);

	void modifyStatus(String status, Long issueId);

	void modifyTitle(String toEntity, Long issueId);

	void modifyContent(String content, Long issueId);

	void modifyMilestone(Long milestoneId, Long issueId);

	void deleteIssueAssigneesBy(Long issueId);

	void deleteIssueLabelsBy(Long issueId);

	List<User> findAssigneesBy(Long issueId);

	List<Label> findLabelsBy(Long issueId);

	boolean exist(Long issueId);
}
