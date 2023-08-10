package org.presents.issuetracker.milestone;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.presents.issuetracker.annotation.RepositoryTest;
import org.presents.issuetracker.milestone.entity.vo.MilestonePreview;
import org.presents.issuetracker.milestone.repository.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@RepositoryTest
public class MilestoneRepositoryTest {

	private final MilestoneRepository milestoneRepository;

	@Autowired
	public MilestoneRepositoryTest(NamedParameterJdbcTemplate jdbcTemplate) {
		milestoneRepository = new MilestoneRepository(jdbcTemplate);
	}

	@Test
	@DisplayName("이슈 아이디를 입력받아 이슈가 가지는 마일스톤을 조회한다.")
	public void findByIssueId() {
		//given
		Long issueId = 1L;

		//when
		MilestonePreview milestone = milestoneRepository.findByIssueId(issueId);

		//then
		Long expectedMilestoneId = 1L;
		assertThat(milestone.getId()).isEqualTo(expectedMilestoneId);
	}

	@Test
	@DisplayName("마일스톤의 필터용 목록을 조회한다.")
	public void findPreviews() {
		//when
		List<MilestonePreview> milestones = milestoneRepository.findPreviews();

		//then
		int expectedMilestoneCount = 5;
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(milestones).hasSize(expectedMilestoneCount);
			for (MilestonePreview milestone : milestones) {
				softAssertions.assertThat(milestone.getId()).isNotNull();
				softAssertions.assertThat(milestone.getName()).isNotNull();
				softAssertions.assertThat(milestone.getProgress()).isNotNull();
			}
			softAssertions.assertAll();
		});
	}
}
