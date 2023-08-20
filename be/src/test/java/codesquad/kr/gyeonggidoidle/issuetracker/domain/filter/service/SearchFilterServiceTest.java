package codesquad.kr.gyeonggidoidle.issuetracker.domain.filter.service;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.ServiceTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.filter.service.information.FilterInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.LabelRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.MemberRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.vo.MemberDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.MilestoneRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.StatRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.IssueByMilestoneVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ServiceTest
class SearchFilterServiceTest {

    @InjectMocks
    private FilterService filterService;

    @Mock
    private StatRepository statRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private LabelRepository labelRepository;
    @Mock
    private MilestoneRepository milestoneRepository;

    @DisplayName("레포지토리에서 메인 페이지의 필터 정보를 받아 FilterListInformation으로 변환할 수 있다.")
    @Test
    void transformFilterVO() {
        //given
        given(memberRepository.getMemberFilter()).willReturn(createDummyMemberDetailsVOs());
        given(labelRepository.getLabelFilter()).willReturn(createDummyLabelDetailsVOs());
        given(milestoneRepository.getMilestoneFilter()).willReturn(createDummyMilestoneDetailVOs());

        //when
        FilterInformation actual = filterService.getMainFilter();

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual.getAssigneeFilterInformations()).hasSize(4);
            assertions.assertThat(actual.getAuthorFilterInformations()).hasSize(3);
            assertions.assertThat(actual.getAuthorFilterInformations().get(0).getName())
                    .isEqualTo("ati");
            assertions.assertThat(actual.getLabelFilterInformations()).hasSize(3);
            assertions.assertThat(actual.getLabelFilterInformations().get(0).getId())
                    .isEqualTo(3L);
            assertions.assertThat(actual.getMilestoneFilterInformations()).hasSize(2);
        });
    }

    @DisplayName("레포지토리에서 이슈 페이지의 필터 정보를 받아 FilterListInformation으로 변환할 수 있다.")
    @Test
    void transformFiltersFromIssue() {
        //given
        given(memberRepository.getMemberFilter()).willReturn(createDummyMemberDetailsVOs());
        given(labelRepository.getLabelFilter()).willReturn(createDummyLabelDetailsVOs());
        given(milestoneRepository.getMilestoneFilter()).willReturn(createDummyMilestoneDetailVOs());
        given(statRepository.countIssuesByMilestoneIds(any())).willReturn(createDummyIssueByMilestoneVOs());

        //when
        FilterInformation actual = filterService.getDetailFilter();

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual.getAssigneeFilterInformations()).hasSize(3);
            assertions.assertThat(actual.getAuthorFilterInformations()).isEmpty();
            assertions.assertThat(actual.getLabelFilterInformations()).hasSize(3);
            assertions.assertThat(actual.getMilestoneFilterInformations()).hasSize(2);
            assertions.assertThat(actual.getMilestoneFilterInformations().get(0).getId())
                    .isEqualTo(0L);
            assertions.assertThat(actual.getMilestoneFilterInformations().get(0).getOpenIssueCount())
                    .isEqualTo(1);
            assertions.assertThat(actual.getMilestoneFilterInformations().get(1).getId())
                    .isEqualTo(1L);
        });
    }

    private Map<Long, IssueByMilestoneVO> createDummyIssueByMilestoneVOs() {
        IssueByMilestoneVO tmp1 = IssueByMilestoneVO.builder()
                .openIssueCount(1)
                .closedIssueCount(2)
                .build();
        IssueByMilestoneVO tmp2 = IssueByMilestoneVO.builder()
                .openIssueCount(3)
                .closedIssueCount(4)
                .build();
        return Map.of(0L, tmp1, 1L, tmp2);
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
}
