package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.Filter;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.vo.IssueVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FilteredIssueRepository {

    public List<IssueVO> findByFilter(Filter filter);
}
