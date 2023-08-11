package org.presents.issuetracker.issue;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.presents.issuetracker.annotation.ServiceTest;
import org.presents.issuetracker.comment.repository.CommentRepository;
import org.presents.issuetracker.issue.dto.request.IssueCreateRequest;
import org.presents.issuetracker.issue.dto.request.IssueUpdateRequest;
import org.presents.issuetracker.issue.entity.Issue;
import org.presents.issuetracker.issue.repository.IssueRepository;
import org.presents.issuetracker.issue.service.IssueService;
import org.presents.issuetracker.label.dto.response.LabelPreviewResponse;
import org.presents.issuetracker.label.entity.Label;
import org.presents.issuetracker.label.repository.LabelRepository;
import org.presents.issuetracker.milestone.dto.response.MilestonePreviewResponse;
import org.presents.issuetracker.milestone.entity.vo.MilestonePreview;
import org.presents.issuetracker.milestone.repository.MilestoneRepository;
import org.presents.issuetracker.user.dto.response.UserResponse;
import org.presents.issuetracker.user.entity.User;
import org.presents.issuetracker.user.repository.UserRepository;

@ServiceTest
public class IssueServiceTest {

	@InjectMocks
	private IssueService issueService;

	@Mock
	private IssueRepository issueRepository;

	@Mock
	private LabelRepository labelRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private MilestoneRepository milestoneRepository;

	@Mock
	private CommentRepository commentRepository;

	@Test
	@DisplayName("이슈 생성 시 이슈 생성 후 이슈에 담당자, 레이블, 마일스톤을 추가한다.")
	public void create() {
		//given
		IssueCreateRequest issueCreateRequest = new IssueCreateRequest("제목", "내용", 1L,
			List.of(1L, 2L), List.of(1L, 2L), 1L);
		Long expectedIssueId = 7L;
		given(issueRepository.save(any())).willReturn(expectedIssueId);
		doNothing().when(issueRepository).addAssignees(any());
		doNothing().when(issueRepository).addLabels(any());
		doNothing().when(issueRepository).setMilestone(anyLong(), anyLong());

		//when
		Long issueId = issueService.create(issueCreateRequest);

		//then
		verify(issueRepository, times(1)).addAssignees(any());
		verify(issueRepository, times(1)).addLabels(any());
		verify(issueRepository, times(1)).setMilestone(anyLong(), anyLong());
		assertThat(issueId).isEqualTo(expectedIssueId);
	}

	@Test
	@DisplayName("수정할 이슈 데이터를 입력 받아서 이슈를 수정하고 이슈 아이디를 반환한다.")
	public void updateTitle() {
		//given
		IssueUpdateRequest issueUpdateRequest = new IssueUpdateRequest(1L, "수정할 제목", "수정할 내용");
		given(issueRepository.existsById(anyLong())).willReturn(true);
		doNothing().when(issueRepository).updateTitle(any(Issue.class));

		//when
		Long issueId = issueService.updateTitle(issueUpdateRequest);

		//then
		assertThat(issueId).isEqualTo(issueUpdateRequest.getId());
	}

	@Test
	@DisplayName("이슈의 레이블을 모두 삭제하고 입력받은 레이블을 이슈에 추가하고 추가된 레이블 목록을 반환한다.")
	public void updateLabels() {
		//given
		List<Long> labelIds = List.of(1L, 2L);
		Long issueId = 1L;
		List<Label> labels = List.of(Label.of(1L, "레이블1", "backgrounColor1", "textColor1"),
			Label.of(2L, "레이블2", "backgrounColor2", "textColor2"));
		given(issueRepository.existsById(anyLong())).willReturn(true);
		given(labelRepository.findByIssueId(anyLong())).willReturn(labels);
		doNothing().when(issueRepository).deleteAllLabel(anyLong());
		doNothing().when(issueRepository).addLabels(any());

		//when
		List<LabelPreviewResponse> updatedLabels = issueService.updateLabels(labelIds, issueId);

		//then
		SoftAssertions.assertSoftly(softAssertions -> {
			for (int i = 0; i < updatedLabels.size(); i++) {
				softAssertions.assertThat(updatedLabels.get(i).getId()).isEqualTo(labels.get(i).getId());
				softAssertions.assertThat(updatedLabels.get(i).getName()).isEqualTo(labels.get(i).getName());
				softAssertions.assertThat(updatedLabels.get(i).getBackgroundColor())
					.isEqualTo(labels.get(i).getBackgroundColor());
				softAssertions.assertThat(updatedLabels.get(i).getTextColor()).isEqualTo(labels.get(i).getTextColor());
				softAssertions.assertThat(updatedLabels.size()).isEqualTo(labels.size());
				softAssertions.assertAll();
			}
		});
	}

	@Test
	@DisplayName("이슈의 담당자를 모두 삭제하고 입력받은 담당자를 이슈에 추가하고 추가된 담당자 목록을 반환한다.")
	public void updateAssignees() {
		//given
		List<Long> assigneeIds = List.of(1L, 2L);
		Long issueId = 1L;
		List<User> assignees = List.of(User.builder().userId(1L).loginId("id1").build(),
			User.builder().userId(2L).loginId("id2").build());
		given(issueRepository.existsById(anyLong())).willReturn(true);
		given(userRepository.findByIssueId(anyLong())).willReturn(assignees);
		doNothing().when(issueRepository).deleteAllAssignee(anyLong());
		doNothing().when(issueRepository).addAssignees(any());

		//when
		List<UserResponse> updatedAssignees = issueService.updateAssignees(assigneeIds, issueId);

		//then
		SoftAssertions.assertSoftly(softAssertions -> {
			for (int i = 0; i < updatedAssignees.size(); i++) {
				softAssertions.assertThat(updatedAssignees.get(i).getUserId()).isEqualTo(assignees.get(i).getUserId());
				softAssertions.assertThat(updatedAssignees.get(i).getLoginId())
					.isEqualTo(assignees.get(i).getLoginId());
				softAssertions.assertThat(updatedAssignees.size()).isEqualTo(assignees.size());
				softAssertions.assertAll();
			}
		});
	}

	@Test
	@DisplayName("이슈의 마일스톤을 입력받은 마일스톤으로 수정하고 수정된 마일스톤을 반환한다.")
	public void updateMilestone() {
		//given
		Long milestoneId = 1L;
		Long issueId = 1L;
		MilestonePreview milestone = MilestonePreview.builder().id(1L).name("마일스톤").build();
		given(issueRepository.existsById(anyLong())).willReturn(true);
		given(milestoneRepository.findByIssueId(anyLong())).willReturn(milestone);
		doNothing().when(issueRepository).setMilestone(anyLong(), anyLong());

		//when
		MilestonePreviewResponse updatedMilestone = issueService.updateMilestone(milestoneId, issueId);

		//then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(updatedMilestone.getId()).isEqualTo(milestone.getId());
			softAssertions.assertThat(updatedMilestone.getName()).isEqualTo(milestone.getName());
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("이슈 삭제 시 레이블, 담당자, 마일스톤, 코멘트도 함께 삭제한다.")
	public void delete() {
		//given
		Long issueId = 1L;
		given(issueRepository.existsById(anyLong())).willReturn(true);
		doNothing().when(issueRepository).deleteAllLabel(issueId);
		doNothing().when(issueRepository).deleteAllAssignee(issueId);
		doNothing().when(issueRepository).deleteMilestone(issueId);
		doNothing().when(issueRepository).delete(issueId);
		doNothing().when(commentRepository).deleteByIssueId(issueId);

		//when
		issueService.delete(issueId);

		//then
		verify(issueRepository, times(1)).deleteAllLabel(anyLong());
		verify(issueRepository, times(1)).deleteAllAssignee(anyLong());
		verify(issueRepository, times(1)).deleteMilestone(anyLong());
		verify(issueRepository, times(1)).delete(anyLong());
		verify(commentRepository, times(1)).deleteByIssueId(anyLong());
	}
}
