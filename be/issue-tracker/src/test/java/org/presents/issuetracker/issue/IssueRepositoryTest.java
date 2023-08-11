package org.presents.issuetracker.issue;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.sql.DataSource;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.presents.issuetracker.annotation.RepositoryTest;
import org.presents.issuetracker.issue.entity.Assignee;
import org.presents.issuetracker.issue.entity.Issue;
import org.presents.issuetracker.issue.entity.IssueLabel;
import org.presents.issuetracker.issue.repository.IssueRepository;
import org.presents.issuetracker.label.entity.Label;
import org.presents.issuetracker.label.repository.LabelRepository;
import org.presents.issuetracker.user.entity.User;
import org.presents.issuetracker.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@RepositoryTest
public class IssueRepositoryTest {

	private final IssueRepository issueRepository;

	private final UserRepository userRepository;

	private final LabelRepository labelRepository;

	@Autowired
	public IssueRepositoryTest(NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource) {
		issueRepository = new IssueRepository(jdbcTemplate);
		userRepository = new UserRepository(jdbcTemplate);
		labelRepository = new LabelRepository(jdbcTemplate, dataSource);
	}

	@Test
	@DisplayName("이슈 아이디로 이슈 정보를 조회한다.")
	public void findById() {
		//given
		Long issueId = 1L;

		//when
		Issue issue = issueRepository.findById(issueId);

		//then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(issue.getId()).isEqualTo(1L);
			softAssertions.assertThat(issue.getTitle()).isEqualTo("기능 구현1");
			softAssertions.assertThat(issue.getContents()).isEqualTo("내용 컨텐츠 내용 컨텐츠 내용 컨텐츠");
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("작성자 아이디, 제목, 내용을 입력받아 새로운 이슈를 생성한다.")
	public void save() {
		//given
		Issue issue = Issue.builder()
			.authorId(1L)
			.title("새로운 이슈")
			.contents("새로운 내용")
			.build();

		//when
		Long savedIssueId = issueRepository.save(issue);

		//then
		assertThat(savedIssueId).isEqualTo(7L);
	}

	@Test
	@DisplayName("여러 개의 담당자 아이디를 입력받아 이슈에 추가한다.")
	public void addAssignees() {
		//given
		Long issueId = 3L;
		Assignee assignee1 = Assignee.builder().issueId(issueId).userId(1L).build();
		Assignee assignee2 = Assignee.builder().issueId(issueId).userId(2L).build();
		List<Assignee> assignees = List.of(assignee1, assignee2);

		//when
		issueRepository.addAssignees(assignees);

		//then
		List<User> assigneeUsers = userRepository.findByIssueId(issueId);
		SoftAssertions.assertSoftly(softAssertions -> {
			for (int i = 0; i < assigneeUsers.size(); i++) {
				softAssertions.assertThat(assigneeUsers.get(i).getUserId()).isEqualTo(assignees.get(i).getUserId());
			}
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("이슈의 담당자를 모두 삭제한다.")
	public void deleteAllAssignee() {
		//given
		Long issueId = 1L;

		//when
		issueRepository.deleteAllAssignee(issueId);

		//then
		List<User> assigneeUsers = userRepository.findByIssueId(issueId);
		assertThat(assigneeUsers.size()).isEqualTo(0);
	}

	@Test
	@DisplayName("여러 개의 레이블 아이디를 입력받아 이슈에 추가한다.")
	public void addLabels() {
		//given
		Long issueId = 3L;
		IssueLabel label1 = IssueLabel.builder().issueId(issueId).labelId(1L).build();
		IssueLabel label2 = IssueLabel.builder().issueId(issueId).labelId(2L).build();
		List<IssueLabel> issueLabels = List.of(label1, label2);

		//when
		issueRepository.addLabels(issueLabels);

		//then
		List<Label> labels = labelRepository.findByIssueId(issueId);
		SoftAssertions.assertSoftly(softAssertions -> {
			for (int i = 0; i < labels.size(); i++) {
				softAssertions.assertThat(labels.get(i).getId()).isEqualTo(issueLabels.get(i).getLabelId());
			}
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("이슈의 레이블을 모두 삭제한다.")
	public void deleteAllLabels() {
		//given
		Long issueId = 1L;

		//when
		issueRepository.deleteAllLabel(issueId);

		//then
		List<Label> labels = labelRepository.findByIssueId(issueId);
		assertThat(labels.size()).isEqualTo(0);
	}

	@Test
	@DisplayName("입력받은 마일스톤 아이디로 이슈의 마일스톤을 수정한다.")
	public void setMilestone() {
		//given
		Long issueId = 1L;
		Long milestoneId = 2L;

		//when
		issueRepository.setMilestone(issueId, milestoneId);

		//then
		Issue issue = issueRepository.findById(issueId);
		assertThat(issue.getMilestoneId()).isEqualTo(milestoneId);
	}

	@Test
	@DisplayName("이슈의 마일스톤을 삭제한다.")
	public void deleteMilestone() {
		//given
		Long issueId = 1L;

		//when
		issueRepository.deleteMilestone(issueId);

		//then
		Issue issue = issueRepository.findById(issueId);
		assertThat(issue.getMilestoneId()).isEqualTo(0);
	}

	@Test
	@DisplayName("이슈의 아이디로 이슈가 존재하는 지 확인한다.")
	public void existsById() {
		//given
		Long issueId = 1L;

		//when
		boolean isExist = issueRepository.existsById(issueId);

		//then
		assertThat(isExist).isTrue();
	}

	@Test
	@DisplayName("이슈의 정보를 수정한다.")
	public void updateTitle() {
		//given
		Long issueId = 1L;
		Issue issue = Issue.builder()
			.id(issueId)
			.title("수정된 제목")
			.build();

		//when
		issueRepository.updateTitle(issue);

		//then
		Issue updatedIssue = issueRepository.findById(issueId);
		assertThat(updatedIssue.getTitle()).isEqualTo(issue.getTitle());
	}

	@Test
	@DisplayName("이슈의 정보를 수정한다.")
	public void updateContents() {
		//given
		Long issueId = 1L;
		Issue issue = Issue.builder()
			.id(issueId)
			.contents("수정된 내용")
			.build();

		//when
		issueRepository.updateContents(issue);

		//then
		Issue updatedIssue = issueRepository.findById(issueId);
		assertThat(updatedIssue.getContents()).isEqualTo(issue.getContents());
	}

	@Test
	@DisplayName("이슈의 상태를 open(열기) 또는 closed(닫기)로 변경한다.")
	public void updateStatus() {
		//given
		List<Long> closeIds = List.of(1L, 2L);
		List<Long> openIds = List.of(4L);
		String close = "closed";
		String open = "open";

		//when
		issueRepository.updateStatus(closeIds, close);
		issueRepository.updateStatus(openIds, open);

		//then
		Issue closeIssue1 = issueRepository.findById(closeIds.get(0));
		Issue closeIssue2 = issueRepository.findById(closeIds.get(1));
		Issue openIssue1 = issueRepository.findById(openIds.get(0));
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(closeIssue1.getStatus()).isEqualTo(close);
			softAssertions.assertThat(closeIssue2.getStatus()).isEqualTo(close);
			softAssertions.assertThat(openIssue1.getStatus()).isEqualTo(open);
		});
	}

	@Test
	@DisplayName("이슈 아이디의 리스트를 입력 받아 리스트에 해당하는 이슈의 개수를 반환한다.")
	public void countByIssueIds() {
		//given
		List<Long> issueIds = List.of(1L, 2L);

		//when
		int count = issueRepository.countByIssueIds(issueIds);

		//then
		assertThat(count).isEqualTo(issueIds.size());
	}

	@Test
	@DisplayName("이슈 아이디에 해당하는 이슈를 삭제한다.")
	public void delete() {
		//given
		Long issueId = 1L;

		//when
		issueRepository.delete(issueId);

		//then
		boolean isExist = issueRepository.existsById(issueId);
		assertThat(isExist).isFalse();
	}
}
