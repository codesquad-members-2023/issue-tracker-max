package codesquard.app.issue.fixture;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import codesquard.app.issue.dto.request.IssueSaveRequest;
import codesquard.app.issue.dto.response.IssueMilestoneCountResponse;
import codesquard.app.issue.dto.response.IssueMilestoneResponse;
import codesquard.app.issue.dto.response.IssueReadResponse;
import codesquard.app.issue.dto.response.IssueUserResponse;
import codesquard.app.issue.entity.IssueStatus;
import codesquard.app.milestone.dto.request.MilestoneSaveRequest;
import codesquard.app.user.service.request.UserSaveServiceRequest;

public class FixtureFactory {

	public static IssueSaveRequest createIssueRegisterRequest(String title, String content, Long milestone) {
		List<Long> labels = new ArrayList<>();
		List<Long> assignees = List.of(1L);
		return new IssueSaveRequest(title, content, milestone, labels, assignees);
	}

	public static UserSaveServiceRequest createUserSaveServiceRequest() {
		String userId = "wis";
		String email = "wis@abcd.com";
		String password = "code1234";
		String avatarUrl = "url";
		return new UserSaveServiceRequest(userId, email, password, password, avatarUrl);
	}

	public static MilestoneSaveRequest createMilestoneCreateRequest(String name) {
		String description = "테스트 코드용";
		LocalDate localDate = LocalDate.now();
		return new MilestoneSaveRequest(name, localDate, description);
	}

	public static IssueReadResponse createIssueReadResponse(Long id) {
		return new IssueReadResponse(id, "제목", IssueStatus.OPENED, LocalDateTime.now(), LocalDateTime.now(),
			LocalDateTime.now(), "제목", new IssueMilestoneResponse(id, "마일스톤", new IssueMilestoneCountResponse(1, 0)),
			new IssueUserResponse(id, "wiz", "url"));
	}
}
