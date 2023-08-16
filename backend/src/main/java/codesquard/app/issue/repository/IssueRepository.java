package codesquard.app.issue.repository;

import java.time.LocalDateTime;
import java.util.List;

import codesquard.app.issue.dto.response.IssueCommentsResponse;
import codesquard.app.issue.dto.response.IssueMilestoneCountResponse;
import codesquard.app.issue.dto.response.IssueReadResponse;
import codesquard.app.issue.entity.Issue;
import codesquard.app.issue.entity.IssueStatus;
import codesquard.app.label.entity.Label;
import codesquard.app.user.entity.User;

public interface IssueRepository {

	Long save(Issue issue);

	List<Issue> findAll();

	IssueReadResponse findBy(Long id);

	Long modify(Issue issue);

	void deleteBy(Long id);

	void saveIssueLabel(Long issueId, List<Long> labels);

	void saveIssueAssignee(Long issueId, List<Long> assignees);

	void modifyTitle(String toEntity, Long issueId, LocalDateTime now);

	void modifyContent(String content, Long issueId, LocalDateTime now);

	void modifyMilestone(Long milestoneId, Long issueId);

	void deleteIssueAssigneesBy(Long issueId);

	void deleteIssueLabelsBy(Long issueId);

	List<User> findAssigneesBy(Long issueId);

	List<Label> findLabelsBy(Long issueId);

	boolean isExist(Long issueId);

	IssueMilestoneCountResponse countIssueBy(Long id);

	Long countIssueByStatus(IssueStatus status);

	List<IssueCommentsResponse> findCommentsBy(Long issueId, Long userId);

	boolean isSameIssueAuthor(Long issueId, Long userId);

	void modifyStatuses(String name, List<Long> issues, LocalDateTime now);
}
