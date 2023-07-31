package codesquard.app.issue.fixture;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import codesquard.app.issue.dto.request.IssueRegisterRequest;
import codesquard.app.milestone.dto.request.MilestoneCreateRequest;
import codesquard.app.user.dto.request.UserSIgnUpRequest;

public class FixtureFactory {

	public static IssueRegisterRequest createIssueRegisterRequest(String title) {
		String content = "내용";
		Long milestone = 1L;
		List<Long> labels = new ArrayList<>();
		List<Long> assignees = List.of(1L);
		return new IssueRegisterRequest(title, content, milestone, labels, assignees);
	}

	public static UserSIgnUpRequest createUserSIgnUpRequest(String userId, String email) {
		String password = "code1234";
		String avatarUrl = "url";
		return new UserSIgnUpRequest(userId, email, password, avatarUrl);
	}

	public static MilestoneCreateRequest createMilestoneCreateRequest(String name) {
		String description = "테스트 코드용";
		LocalDate localDate = LocalDate.now();
		return new MilestoneCreateRequest(name, description, localDate);
	}
}
