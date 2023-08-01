package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.vo.IssueVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.information.LabelInformation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class IssueInformation {

    private final Long id;
    private final String title;
    private final String author;
    private final List<String> assigneeProfiles;
    private final String milestone;
    private final List<LabelInformation> labelInformations;
    private final LocalDateTime createdAt;

    @Builder
    private IssueInformation(Long id, String title, String author, List<String> assigneeProfiles, String milestone, List<LabelInformation> labelInformations, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.assigneeProfiles = assigneeProfiles;
        this.milestone = milestone;
        this.labelInformations = labelInformations;
        this.createdAt = createdAt;
    }

    public static List<IssueInformation> from(List<IssueVO> issueVOs, Map<Long, List<LabelVO>> labelVOs,
                                              Map<Long, List<String>> assigneeProfiles) {
        return issueVOs.stream()
                .map(issueVO -> from(issueVO, labelVOs, assigneeProfiles))
                .collect(Collectors.toUnmodifiableList());
    }

    public static IssueInformation from(IssueVO issueVO, Map<Long, List<LabelVO>> labelVOs,
                                        Map<Long, List<String>> assigneeProfiles) {
        return IssueInformation.builder()
                .id(issueVO.getId())
                .title(issueVO.getTitle())
                .author(issueVO.getAuthor())
                .assigneeProfiles(assigneeProfiles.get(issueVO.getId()))
                .milestone(issueVO.getMilestone())
                .labelInformations(LabelInformation.from(labelVOs.get(issueVO.getId())))
                .createdAt(issueVO.getCreatedAt())
                .build();
    }
}
