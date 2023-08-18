package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.ServiceTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.repository.CommentRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.comment.repository.result.CommentResult;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.IssueRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.IssueSearchRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.result.IssueDetailResult;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.repository.result.IssueSearchResult;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.IssueDetailInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.SearchInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.LabelRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.MemberRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.MilestoneRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.StatRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.StatVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ServiceTest
class IssueServiceTest {

    @InjectMocks
    private IssueService issueService;

    @Mock
    private IssueRepository issueRepository;
    @Mock
    private IssueSearchRepository issueSearchRepository;
    @Mock
    private StatRepository statRepository;
    @Mock
    private LabelRepository labelRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MilestoneRepository milestoneRepository;
    @Mock
    private CommentRepository commentRepository;

    @DisplayName("레포지토리에서 열린 이슈 관련 정보들을 받고 FilterInformation으로 변환할 수 있다.")
    @Test
    void transformOpenIssuesVO() {
        //given
        given(statRepository.countOverallStats()).willReturn(createDummyStatVO());
        given(issueSearchRepository.findBySearchFilter(any())).willReturn(createDummyOpenIssueVOs());
        given(labelRepository.findAllByIssueIds(any())).willReturn(createDummyLabelVOs());
        given(memberRepository.findAllProfilesByIssueIds(any())).willReturn(createDummyAssigneeProfiles());

        //when
        SearchInformation actual = issueService.findIssuesBySearchFilter("is:open");

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual.getOpenIssueCount()).isEqualTo(3);
            assertions.assertThat(actual.getLabelCount()).isEqualTo(4);
            assertions.assertThat((actual.getFilter())).isEqualTo("is:open");
            assertions.assertThat(actual.getIssueInformations()).hasSize(5);
            assertions.assertThat(actual.getIssueInformations().get(3).getAuthor())
                    .isEqualTo("작성자 1");
            assertions.assertThat(actual.getIssueInformations().get(2).getLabelInformations())
                    .hasSize(4);
            assertions.assertThat(actual.getIssueInformations().get(2).getLabelInformations().get(2).getBackgroundColor())
                    .isEqualTo("배경색 3");
            assertions.assertThat(actual.getIssueInformations().get(4).getAssigneeProfiles().get(3))
                    .isEqualTo("담당자 4");
        });
    }

    @DisplayName("레포지토리에서 닫힌 이슈 관련 정보들을 받고 FilterInformation으로 변환할 수 있다.")
    @Test
    void transformClosedIssuesVO() {
        //given
        given(statRepository.countOverallStats()).willReturn(createDummyStatVO());
        given(issueSearchRepository.findBySearchFilter(any())).willReturn((createDummyClosedIssueVOs()));
        given(labelRepository.findAllByIssueIds(any())).willReturn(createDummyLabelVOs());
        given(memberRepository.findAllProfilesByIssueIds(any())).willReturn(createDummyAssigneeProfiles());

        //when
        SearchInformation actual = issueService.findIssuesBySearchFilter("is:closed");

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual.getClosedIssueCount()).isEqualTo(3);
            assertions.assertThat(actual.getMilestoneCount()).isEqualTo(2);
            assertions.assertThat((actual.getFilter())).isEqualTo("is:closed");
            assertions.assertThat(actual.getIssueInformations()).hasSize(5);
            assertions.assertThat(actual.getIssueInformations().get(0).getMilestone())
                    .isEqualTo("마일스톤 1");
            assertions.assertThat(actual.getIssueInformations().get(4).getLabelInformations())
                    .isEqualTo(Collections.emptyList());
            assertions.assertThat(actual.getIssueInformations().get(1).getAssigneeProfiles().get(0))
                    .isEqualTo("담당자 4");
        });
    }

    @DisplayName("레포지토리들에서 받은 이슈 상세에 대한 정보를 IssueDetailInformation으로 변환한다.")
    @Test
    void transformToIssueDetailInformation() {
        given(issueRepository.findByIssueId(5L)).willReturn(createDummyIssueDetailResult());
        given(memberRepository.findAllProfilesByIssueId(5L)).willReturn(createDummyAssigneeProfileList());
        given(statRepository.countCommentStatsByIssueId(5L)).willReturn(4);
        given(labelRepository.findAllByIssueId(5L)).willReturn(createDummyLabelVOList());
        given(milestoneRepository.getMilestoneByIssueId(5L)).willReturn(createDummyMilestoneVO());
        given(commentRepository.findByIssueId(5L)).willReturn(createDummyCommentResults());
        Long issueId = 5L;

        //when
        IssueDetailInformation actual = issueService.getIssueDetailByIssueId(issueId);

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual.getCommentCount()).isEqualTo(4);
            assertions.assertThat(actual.getAuthor()).isEqualTo("작성자");
            assertions.assertThat(actual.getAssigneeProfiles()).hasSize(2);
            assertions.assertThat(actual.getLabelInformations().get(1).getTextColor()).isEqualTo("textColor2");
            assertions.assertThat(actual.getMilestoneInformation().getOpenIssueCount()).isEqualTo(1);
            assertions.assertThat(actual.getCommentInformations().get(3).getAuthorId()).isEqualTo(8);
        });
    }

    private IssueDetailResult createDummyIssueDetailResult() {
        return IssueDetailResult.builder()
                .id(5L)
                .author("작성자")
                .title("제목")
                .isOpen(true)
                .createdAt(LocalDateTime.of(2000, 1, 1, 1, 1))
                .build();
    }

    private List<String> createDummyAssigneeProfileList() {
        return List.of("profile 1", "profile 2");
    }

    private List<LabelVO> createDummyLabelVOList() {
        LabelVO labelVO1 = LabelVO.builder()
                .name("라벨1")
                .textColor("textColor1")
                .backgroundColor("backgroundColor1")
                .build();
        LabelVO labelVO2 = LabelVO.builder()
                .name("라벨2")
                .textColor("textColor2")
                .backgroundColor("backgroundColor2")
                .build();
        LabelVO labelVO3 = LabelVO.builder()
                .name("라벨3")
                .textColor("textColor3")
                .backgroundColor("backgroundColor3")
                .build();

        return List.of(labelVO1, labelVO2, labelVO3);
    }

    private MilestoneVO createDummyMilestoneVO() {
        return MilestoneVO.builder()
                .name("마일스톤")
                .openIssueCount(1)
                .closedIssueCount(2)
                .build();
    }

    private List<CommentResult> createDummyCommentResults() {
        CommentResult commentResult1 = CommentResult.builder()
                .id(1L)
                .authorId(2L)
                .authorName("작성자1")
                .contents("내용1")
                .createdAt(LocalDateTime.now())
                .build();
        CommentResult commentResult2 = CommentResult.builder()
                .id(3L)
                .authorId(4L)
                .authorName("작성자2")
                .contents("내용2")
                .createdAt(LocalDateTime.now())
                .build();
        CommentResult commentResult3 = CommentResult.builder()
                .id(5L)
                .authorId(6L)
                .authorName("작성자3")
                .contents("내용3")
                .createdAt(LocalDateTime.now())
                .build();
        CommentResult commentResult4 = CommentResult.builder()
                .id(7L)
                .authorId(8L)
                .authorName("작성자4")
                .contents("내용4")
                .createdAt(LocalDateTime.now())
                .build();

        return List.of(commentResult1, commentResult2, commentResult3, commentResult4);
    }

    private StatVO createDummyStatVO() {
        return StatVO.builder()
                .openIssueCount(3)
                .closedIssueCount(3)
                .labelCount(4)
                .milestoneCount(2)
                .build();
    }

    private List<IssueSearchResult> createDummyOpenIssueVOs() {
        IssueSearchResult issueSearchResult1 = IssueSearchResult.builder()
                .id(1L)
                .author("작성자 1")
                .milestone("마일스톤 1")
                .title("제목 1")
                .createdAt(LocalDateTime.now())
                .build();
        IssueSearchResult issueSearchResult2 = IssueSearchResult.builder()
                .id(2L)
                .author("작성자 2")
                .milestone("마일스톤 2")
                .title("제목 2")
                .createdAt(LocalDateTime.now())
                .build();
        IssueSearchResult issueSearchResult3 = IssueSearchResult.builder()
                .id(3L)
                .author("작성자 1")
                .milestone("마일스톤 2")
                .title("제목 3")
                .createdAt(LocalDateTime.now())
                .build();
        IssueSearchResult issueSearchResult4 = IssueSearchResult.builder()
                .id(4L)
                .author("작성자 1")
                .milestone("마일스톤 2")
                .title("제목 4")
                .createdAt(LocalDateTime.now())
                .build();
        IssueSearchResult issueSearchResult5 = IssueSearchResult.builder()
                .id(5L)
                .author("작성자 2")
                .milestone("마일스톤 1")
                .title("제목 5")
                .createdAt(LocalDateTime.now())
                .build();

        return List.of(issueSearchResult1, issueSearchResult2, issueSearchResult3, issueSearchResult4, issueSearchResult5);
    }

    private List<IssueSearchResult> createDummyClosedIssueVOs() {
        IssueSearchResult issueSearchResult1 = IssueSearchResult.builder()
                .id(1L)
                .author("작성자 1")
                .milestone("마일스톤 1")
                .title("제목 1")
                .createdAt(LocalDateTime.now())
                .build();
        IssueSearchResult issueSearchResult2 = IssueSearchResult.builder()
                .id(2L)
                .author("작성자 2")
                .milestone("마일스톤 2")
                .title("제목 2")
                .createdAt(LocalDateTime.now())
                .build();
        IssueSearchResult issueSearchResult3 = IssueSearchResult.builder()
                .id(3L)
                .author("작성자 1")
                .milestone("마일스톤 2")
                .title("제목 3")
                .createdAt(LocalDateTime.now())
                .build();
        IssueSearchResult issueSearchResult4 = IssueSearchResult.builder()
                .id(4L)
                .author("작성자 1")
                .milestone("마일스톤 2")
                .title("제목 4")
                .createdAt(LocalDateTime.now())
                .build();
        IssueSearchResult issueSearchResult5 = IssueSearchResult.builder()
                .id(5L)
                .author("작성자 2")
                .milestone("마일스톤 1")
                .title("제목 5")
                .createdAt(LocalDateTime.now())
                .build();

        return List.of(issueSearchResult1, issueSearchResult2, issueSearchResult3, issueSearchResult4, issueSearchResult5);
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
