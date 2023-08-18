package codesquad.issueTracker.issue.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import annotation.RepositoryTest;
import codesquad.issueTracker.issue.domain.Issue;

@RepositoryTest
class IssueRepositoryTest {

	private IssueRepository issueRepository;

	@Autowired
	public IssueRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.issueRepository = new IssueRepository(jdbcTemplate);
	}

	@Test
	@DisplayName("title, content, milestone_id, user_id insert 테스트")
	void insert() {
		// given
		Issue issue = Issue.builder()
			.title("제목")
			.content("추가 한 내용")
			.milestoneId(1L)
			.userId(1L)
			.build();
		// when
		Long id = issueRepository.insert(issue);
		// then
		assertThat(id).isEqualTo(1L);

	}

	@Test
	void insertLabels() {
		// given
		List<Long> request = List.of(1L, 2L);
		// when
		Long first = issueRepository.insertLabels(1L, request.get(0));
		Long second = issueRepository.insertLabels(1L, request.get(1));
		// then
		assertThat(first).isEqualTo(request.get(0));
		assertThat(second).isEqualTo(request.get(1));
	}

	@Test
	void insertAssignees() {
		// given
		List<Long> request = List.of(1L, 2L);
		// when
		Long first = issueRepository.insertAssignees(1L, request.get(0));
		Long second = issueRepository.insertAssignees(1L, request.get(1));
		// then
		assertThat(first).isEqualTo(request.get(0));
		assertThat(second).isEqualTo(request.get(1));
	}

}