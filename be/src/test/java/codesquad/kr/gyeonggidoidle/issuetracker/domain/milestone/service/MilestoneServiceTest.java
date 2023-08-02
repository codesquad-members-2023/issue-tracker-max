package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.ServiceTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.MilestoneRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.IssueByMilestoneVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.repository.vo.MilestoneDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.information.MilestonePageInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.StatRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.MilestoneStatVO;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;


@ServiceTest
class MilestoneServiceTest {

    @InjectMocks
    MilestoneService milestoneService;
    @Mock
    StatRepository statRepository;

    @Mock
    MilestoneRepository milestoneRepository;

    @DisplayName("레포지토리에서 열린 마일스톤 정보들을 받고 MilestonePageInformation으로 변환할 수 있다.")
    @Test
    void testReadOpenMilestonePage() {
        given(statRepository.countMilestoneStats()).willReturn(createDummyMilestoneStatVO());
        given(milestoneRepository.findOpenMilestones()).willReturn(createDummyMilestoneDetailsVOs());
        given(statRepository.findIssuesCountByMilestoneIds(any())).willReturn(createDummyIssueByMilestoneVOs());

        MilestonePageInformation actual = milestoneService.readOpenMilestonePage();

        assertThat(actual.getOpenMilestoneCount()).isEqualTo(1);
        assertThat(actual.getMilestoneDetailsInformations().get(0).getDueDate()).isEqualTo("2023-08-02");
        assertThat(actual.getMilestoneDetailsInformations().get(1).getDueDate()).isEqualTo("1998-10-27");
        assertThat(actual.getMilestoneDetailsInformations().size()).isEqualTo(3);

    }

    private MilestoneStatVO createDummyMilestoneStatVO() {
        return MilestoneStatVO.builder()
                .openMilestoneCount(1)
                .closeMilestoneCount(2)
                .labelCount(3)
                .build();
    }

    private List<MilestoneDetailsVO> createDummyMilestoneDetailsVOs() {
        MilestoneDetailsVO tmp1 = MilestoneDetailsVO.builder()
                .id(1L)
                .name("tmp1")
                .description("test")
                .dueDate(LocalDate.now())
                .openIssueCount(1)
                .closedIssuesCount(2)
                .build();
        MilestoneDetailsVO tmp2 = MilestoneDetailsVO.builder()
                .id(2L)
                .name("tmp2")
                .description("test")
                .dueDate(LocalDate.of(1998,10,27))
                .openIssueCount(3)
                .closedIssuesCount(4)
                .build();
        MilestoneDetailsVO tmp3 = MilestoneDetailsVO.builder()
                .id(3L)
                .name("tmp3")
                .description("test")
                .dueDate(LocalDate.now())
                .openIssueCount(5)
                .closedIssuesCount(6)
                .build();
        return List.of(tmp1,tmp2,tmp3);
    }

    private Map<Long, IssueByMilestoneVO> createDummyIssueByMilestoneVOs() {
        IssueByMilestoneVO tmp1 = IssueByMilestoneVO.builder()
                .openIssueCount(1)
                .closedIssueCount(1)
                .build();
        IssueByMilestoneVO tmp2 = IssueByMilestoneVO.builder()
                .openIssueCount(2)
                .closedIssueCount(2)
                .build();
        IssueByMilestoneVO tmp3 = IssueByMilestoneVO.builder()
                .openIssueCount(3)
                .closedIssueCount(3)
                .build();
        return Map.of(1L, tmp1, 2L, tmp2, 3L, tmp3);
    }
}
