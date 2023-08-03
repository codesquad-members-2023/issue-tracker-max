package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.ServiceTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.IssueRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.vo.IssueVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.FilterInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.FilterListInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.LabelRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.MemberRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.vo.MemberDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.MilestoneRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.StatRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.StatVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ServiceTest
public class IssueServiceTest {

    @InjectMocks
    IssueService issueService;
    @Mock
    StatRepository statRepository;
    @Mock
    IssueRepository issueRepository;
    @Mock
    LabelRepository labelRepository;
    @Mock
    MemberRepository memberRepository;

    @Mock
    MilestoneRepository milestoneRepository;

    @DisplayName("레포지토리에서 열린 이슈 관련 정보들을 받고 FilterInformation으로 변환할 수 있다.")
    @Test
    void readOpenIssuesTest() {
        given(statRepository.countOverallStats()).willReturn(createDummyStatVO());
        given(issueRepository.findOpenIssues()).willReturn((createDummyOpenIssueVOs()));
        given(labelRepository.findAllByIssueIds(any())).willReturn(createDummyLabelVOs());
        given(memberRepository.findAllProfilesByIssueIds(any())).willReturn(createDummyAssigneeProfiles());

        FilterInformation actual = issueService.readOpenIssues();

        assertThat(actual.getOpenIssueCount()).isEqualTo(3);
        assertThat(actual.getLabelCount()).isEqualTo(4);
        assertThat(actual.getIssueInformations()).hasSize(5);
        assertThat(actual.getIssueInformations().get(3).getAuthor()).isEqualTo("작성자 1");
        assertThat(actual.getIssueInformations().get(2).getLabelInformations()).hasSize(4);
        assertThat(actual.getIssueInformations().get(2).getLabelInformations().get(2).getBackgroundColor())
                .isEqualTo("배경색 3");
        assertThat(actual.getIssueInformations().get(4).getAssigneeProfiles().get(3)).isEqualTo("담당자 4");
    }

    @DisplayName("레포지토리에서 닫힌 이슈 관련 정보들을 받고 FilterInformation으로 변환할 수 있다.")
    @Test
    void readClosedIssuesTest() {
        given(statRepository.countOverallStats()).willReturn(createDummyStatVO());
        given(issueRepository.findClosedIssues()).willReturn((createDummyClosedIssueVOs()));
        given(labelRepository.findAllByIssueIds(any())).willReturn(createDummyLabelVOs());
        given(memberRepository.findAllProfilesByIssueIds(any())).willReturn(createDummyAssigneeProfiles());

        FilterInformation actual = issueService.readClosedIssues();

        assertThat(actual.getClosedIssueCount()).isEqualTo(3);
        assertThat(actual.getMilestoneCount()).isEqualTo(2);
        assertThat(actual.getIssueInformations()).hasSize(5);
        assertThat(actual.getIssueInformations().get(0).getMilestone()).isEqualTo("마일스톤 1");
        assertThat(actual.getIssueInformations().get(4).getLabelInformations()).isEqualTo(List.of());
        assertThat(actual.getIssueInformations().get(1).getAssigneeProfiles().get(0)).isEqualTo("담당자 4");
    }

    @DisplayName("레포지토리에서 필터 정보를 받아 FilterListInformation으로 변환할 수 있다.")
    @Test
    void testReadFilters() {
        given(memberRepository.findAllFilters()).willReturn(createDummyMemberDetailsVOs());
        given(labelRepository.findAllFilters()).willReturn(createDummyLabelDetailsVOs());
        given(milestoneRepository.findAllFilters()).willReturn(createDummyMilestoneDetailVOs());

        FilterListInformation actual = issueService.readFilters();

        assertThat(actual.getAssigneeFilterInformations().size()).isEqualTo(4);
        assertThat(actual.getAuthorFilterInformations().size()).isEqualTo(3);
        assertThat(actual.getAuthorFilterInformations().get(0).getName()).isEqualTo("ati");
        assertThat(actual.getLabelFilterInformations().size()).isEqualTo(3);
        assertThat(actual.getLabelFilterInformations().get(0).getId()).isEqualTo(3L);
        assertThat(actual.getMilestoneFilterInformations().size()).isEqualTo(2);
    }

    private List<MemberDetailsVO> createDummyMemberDetailsVOs() {
        MemberDetailsVO tmp1 = MemberDetailsVO.builder()
                .id(3L)
                .name("ati")
                .profile("1234")
                .build();
        MemberDetailsVO tmp2 = MemberDetailsVO.builder()
                .id(2L)
                .name("joy")
                .profile("1234")
                .build();
        MemberDetailsVO tmp3 = MemberDetailsVO.builder()
                .id(1L)
                .name("nag")
                .profile("1234")
                .build();
        return List.of(tmp1, tmp2, tmp3);
    }

    private List<LabelDetailsVO> createDummyLabelDetailsVOs() {
        LabelDetailsVO tmp1 = LabelDetailsVO.builder()
                .id(3L)
                .name("tmp1")
                .backgroundColor("#")
                .textColor("##")
                .build();
        LabelDetailsVO tmp2 = LabelDetailsVO.builder()
                .id(2L)
                .name("tmp2")
                .backgroundColor("#")
                .textColor("##")
                .build();
        LabelDetailsVO tmp3 = LabelDetailsVO.builder()
                .id(1L)
                .name("tmp3")
                .backgroundColor("#")
                .textColor("##")
                .build();
        return List.of(tmp1, tmp2, tmp3);
    }

    private List<MilestoneDetailsVO> createDummyMilestoneDetailVOs() {
        MilestoneDetailsVO tmp1 = MilestoneDetailsVO.builder()
                .id(0L)
                .name("tmp1")
                .build();
        MilestoneDetailsVO tmp2 = MilestoneDetailsVO.builder()
                .id(1L)
                .name("tmp2")
                .build();
        return List.of(tmp1, tmp2);
    }

    private StatVO createDummyStatVO() {
        return StatVO.builder()
                .openIssueCount(3)
                .closedIssueCount(3)
                .labelCount(4)
                .milestoneCount(2)
                .build();
    }

    private List<IssueVO> createDummyOpenIssueVOs() {
        IssueVO issueVO1 = IssueVO.builder()
                .id(1L)
                .author("작성자 1")
                .milestone("마일스톤 1")
                .title("제목 1")
                .createdAt(LocalDateTime.now())
                .build();
        IssueVO issueVO2 = IssueVO.builder()
                .id(2L)
                .author("작성자 2")
                .milestone("마일스톤 2")
                .title("제목 2")
                .createdAt(LocalDateTime.now())
                .build();
        IssueVO issueVO3 = IssueVO.builder()
                .id(3L)
                .author("작성자 1")
                .milestone("마일스톤 2")
                .title("제목 3")
                .createdAt(LocalDateTime.now())
                .build();
        IssueVO issueVO4 = IssueVO.builder()
                .id(4L)
                .author("작성자 1")
                .milestone("마일스톤 2")
                .title("제목 4")
                .createdAt(LocalDateTime.now())
                .build();
        IssueVO issueVO5 = IssueVO.builder()
                .id(5L)
                .author("작성자 2")
                .milestone("마일스톤 1")
                .title("제목 5")
                .createdAt(LocalDateTime.now())
                .build();

        return List.of(issueVO1, issueVO2, issueVO3, issueVO4, issueVO5);
    }

    private List<IssueVO> createDummyClosedIssueVOs() {
        IssueVO issueVO1 = IssueVO.builder()
                .id(1L)
                .author("작성자 1")
                .milestone("마일스톤 1")
                .title("제목 1")
                .createdAt(LocalDateTime.now())
                .build();
        IssueVO issueVO2 = IssueVO.builder()
                .id(2L)
                .author("작성자 2")
                .milestone("마일스톤 2")
                .title("제목 2")
                .createdAt(LocalDateTime.now())
                .build();
        IssueVO issueVO3 = IssueVO.builder()
                .id(3L)
                .author("작성자 1")
                .milestone("마일스톤 2")
                .title("제목 3")
                .createdAt(LocalDateTime.now())
                .build();
        IssueVO issueVO4 = IssueVO.builder()
                .id(4L)
                .author("작성자 1")
                .milestone("마일스톤 2")
                .title("제목 4")
                .createdAt(LocalDateTime.now())
                .build();
        IssueVO issueVO5 = IssueVO.builder()
                .id(5L)
                .author("작성자 2")
                .milestone("마일스톤 1")
                .title("제목 5")
                .createdAt(LocalDateTime.now())
                .build();

        return List.of(issueVO1, issueVO2, issueVO3, issueVO4, issueVO5);
    }

    private Map<Long, List<LabelVO>> createDummyLabelVOs() {
        LabelVO labelVO1 = LabelVO.builder()
                .name("이름 1")
                .backgroundColor("배경색 1")
                .textColor("글자색 1")
                .build();
        LabelVO labelVO2 = LabelVO.builder()
                .name("이름 2")
                .backgroundColor("배경색 2")
                .textColor("글자색 2")
                .build();
        LabelVO labelVO3 = LabelVO.builder()
                .name("이름 3")
                .backgroundColor("배경색 3")
                .textColor("글자색 3")
                .build();
        LabelVO labelVO4 = LabelVO.builder()
                .name("이름 4")
                .backgroundColor("배경색 4")
                .textColor("글자색 4")
                .build();

        return Map.of(1L, List.of(labelVO1, labelVO3),
                2L, List.of(labelVO3, labelVO2),
                3L, List.of(labelVO1, labelVO2, labelVO3, labelVO4),
                4L, List.of(labelVO2),
                5L, List.of());
    }

    private Map<Long, List<String>> createDummyAssigneeProfiles() {
        return Map.of(1L, List.of("담당자 1", "담당자 3"),
                2L, List.of("담당자 4"),
                3L, List.of(),
                4L, List.of("담당자 1", "담당자 2"),
                5L, List.of("담당자 1", "담당자 2", "담당자 3", "담당자 4"));
    }
}
