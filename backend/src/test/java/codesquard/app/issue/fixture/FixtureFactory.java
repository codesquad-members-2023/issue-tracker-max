package codesquard.app.issue.fixture;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import codesquard.app.issue.dto.request.IssueSaveRequest;
import codesquard.app.milestone.dto.request.MilestoneSaveRequest;
import codesquard.app.user.controller.request.UserSaveRequest;

public class FixtureFactory {

	public static IssueSaveRequest createIssueRegisterRequest(String title, Long milestone) {
		String content = "내용";
		List<Long> labels = new ArrayList<>();
		List<Long> assignees = List.of(1L);
		return new IssueSaveRequest(title, content, milestone, labels, assignees);
	}

	public static UserSaveRequest createUserSaveRequest() {
		String userId = "wis";
		String email = "wis@abcd.com";
		String password = "code1234";
		String avatarUrl = "url";
		return new UserSaveRequest(userId, email, password, avatarUrl);
	}

	public static MilestoneSaveRequest createMilestoneCreateRequest(String name) {
		String description = "테스트 코드용";
		LocalDate localDate = LocalDate.now();
		return new MilestoneSaveRequest(name, localDate, description);
	}
}
