package org.presents.issuetracker.issue;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.presents.issuetracker.annotation.MapperTest;
import org.presents.issuetracker.issue.entity.vo.IssueDetailInfo;
import org.presents.issuetracker.issue.mapper.IssueMapper;
import org.springframework.beans.factory.annotation.Autowired;

@MapperTest
public class IssueMapperTest {

	@Autowired
	private IssueMapper issueMapper;

	@Test
	@DisplayName("issueId를 입력받아서 issue의 상세 정보를 조회한다.")
	public void getIssueDetailTest() {
		//given
		Long issueId = 1L;

		//when
		IssueDetailInfo issueDetailInfo = issueMapper.getIssueDetail(issueId);

		//then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(issueDetailInfo.getId()).isEqualTo(1L);
			softAssertions.assertThat(issueDetailInfo.getTitle()).isEqualTo("기능 구현1");
			softAssertions.assertThat(issueDetailInfo.getAuthor().getUserId()).isEqualTo(1L);
			softAssertions.assertThat(issueDetailInfo.getMilestone().getId()).isEqualTo(1L);
			softAssertions.assertThat(issueDetailInfo.getAssignees().size()).isEqualTo(2);
			softAssertions.assertThat(issueDetailInfo.getLabels().size()).isEqualTo(2);
			softAssertions.assertThat(issueDetailInfo.getComments().size()).isEqualTo(2);
			softAssertions.assertAll();
		});
	}
}
