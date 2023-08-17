package codesquard.app.issue.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import codesquard.app.issue.mapper.request.IssueFilterRequest;
import codesquard.app.issue.mapper.response.IssuesResponse;
import codesquard.app.issue.mapper.response.filters.response.MultiFilterAssignee;
import codesquard.app.issue.mapper.response.filters.response.MultiFilterAuthor;
import codesquard.app.issue.mapper.response.filters.response.MultiFilterLabel;
import codesquard.app.issue.mapper.response.filters.response.MultiFilterMilestone;

@Mapper
public interface IssueMapper {

	List<IssuesResponse> getIssues(IssueFilterRequest request);

	List<MultiFilterAssignee> getMultiFiltersAssignees(@Param("check") boolean check,
		@Param("request") IssueFilterRequest request);

	List<MultiFilterAuthor> getMultiFiltersAuthors(@Param("check") boolean check,
		@Param("request") IssueFilterRequest request);

	List<MultiFilterLabel> getMultiFiltersLabels(@Param("check") boolean check,
		@Param("request") IssueFilterRequest request);

	List<MultiFilterMilestone> getMultiFiltersMilestones(@Param("check") boolean check,
		@Param("request") IssueFilterRequest request);

	int countIssueSameAuthor(@Param("id") List<Long> issueId, @Param("user_id") Long userId);

	boolean isNotExist(List<Long> issueId);
}
