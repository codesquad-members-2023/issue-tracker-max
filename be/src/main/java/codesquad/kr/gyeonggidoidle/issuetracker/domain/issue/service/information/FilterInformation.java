package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.vo.IssueVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.StatVO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class FilterInformation {

    private final Integer openIssueCount;
    private final Integer closedIssueCount;
    private final Integer labelCount;
    private final Integer milestoneCount;
    private final String filter;
    private final List<IssueInformation> issueInformations;

    @Builder
    private FilterInformation(Integer openIssueCount, Integer closedIssueCount, Integer labelCount, Integer milestoneCount, String filter, List<IssueInformation> issueInformations) {
        this.openIssueCount = openIssueCount;
        this.closedIssueCount = closedIssueCount;
        this.labelCount = labelCount;
        this.milestoneCount = milestoneCount;
        this.filter = filter;
        this.issueInformations = issueInformations;
    }

    public static FilterInformation from(StatVO statVO, List<IssueVO> issueVOs, Map<Long, List<LabelVO>> labelVOs,
                                         Map<Long, List<String>> assigneeProfiles, String filter) {
        return FilterInformation.builder()
                .openIssueCount(statVO.getOpenIssueCount())
                .closedIssueCount(statVO.getClosedIssueCount())
                .labelCount(statVO.getLabelCount())
                .milestoneCount(statVO.getMilestoneCount())
                .filter(filter)
                .issueInformations(IssueInformation.from(issueVOs, labelVOs, assigneeProfiles))
                .build();
    }
}
