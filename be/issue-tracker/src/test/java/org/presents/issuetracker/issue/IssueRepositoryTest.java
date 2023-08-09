package org.presents.issuetracker.issue;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.presents.issuetracker.annotation.RepositoryTest;
import org.presents.issuetracker.issue.entity.Issue;
import org.presents.issuetracker.issue.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@RepositoryTest
public class IssueRepositoryTest {

	private final IssueRepository issueRepository;

	@Autowired
	public IssueRepositoryTest(NamedParameterJdbcTemplate jdbcTemplate) {
		issueRepository = new IssueRepository(jdbcTemplate);
	}

	@Test
	@DisplayName("작성자 아이디, 제목, 내용을 입력 받아서 새로운 이슈를 생성한다.")
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
}
