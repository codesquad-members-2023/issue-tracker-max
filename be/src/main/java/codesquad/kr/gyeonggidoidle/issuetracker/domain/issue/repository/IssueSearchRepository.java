package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.SearchFilter;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.result.IssueSearchResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IssueSearchRepository {

    public List<IssueSearchResult> findBySearchFilter(SearchFilter searchFilter);
}
