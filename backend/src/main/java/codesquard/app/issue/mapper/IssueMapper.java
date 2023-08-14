package codesquard.app.issue.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import codesquard.app.issue.mapper.request.IssueFilterRequest;
import codesquard.app.issue.mapper.response.IssuesResponse;
import codesquard.app.issue.mapper.response.filters.response.MultiFiltersAssignees;
import codesquard.app.issue.mapper.response.filters.response.MultiFiltersAuthors;
import codesquard.app.issue.mapper.response.filters.response.MultiFiltersLabels;
import codesquard.app.issue.mapper.response.filters.response.MultiFiltersMilestones;

@Mapper
public interface IssueMapper {

	List<IssuesResponse> getIssues(IssueFilterRequest request);

	List<MultiFiltersAssignees> getMultiFiltersAssignees(@Param("check") boolean check,
		@Param("request") IssueFilterRequest request);

	List<MultiFiltersAuthors> getMultiFiltersAuthors(@Param("check") boolean check,
		@Param("request") IssueFilterRequest request);

	List<MultiFiltersLabels> getMultiFiltersLabels(@Param("check") boolean check,
		@Param("request") IssueFilterRequest request);

	List<MultiFiltersMilestones> getMultiFiltersMilestones(@Param("check") boolean check,
		@Param("request") IssueFilterRequest request);

}
