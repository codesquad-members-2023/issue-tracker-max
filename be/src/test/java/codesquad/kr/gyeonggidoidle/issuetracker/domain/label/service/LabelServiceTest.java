package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.ServiceTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.LabelRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.repository.VO.LabelDetailsVO;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.information.LabelPageInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.StatRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.stat.repository.vo.StatVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.BDDMockito.given;

@ServiceTest
class LabelServiceTest {

    @InjectMocks
    LabelService labelService;

    @Mock
    StatRepository statRepository;
    @Mock
    LabelRepository labelRepository;

    @DisplayName("레포지토리에서 라벨 관련 정보들을 받고 LabelPageInformation으로 변환할 수 있다.")
    @Test
    void transformToLabelPageInformation() {
        //given
        given(statRepository.countLabelStats()).willReturn(createDummyStatVO());
        given(labelRepository.findAll()).willReturn(createDummyLabelDetailsVOs());

        //when
        LabelPageInformation actual = labelService.readLabelPage();

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual.getLabelCount()).isEqualTo(3);
            assertions.assertThat(actual.getMilestoneCount()).isEqualTo(4);
            assertions.assertThat(actual.getLabelDetailsInformations()).hasSize(3);
            assertions.assertThat(actual.getLabelDetailsInformations().get(0).getName()).isEqualTo("tmp1");
        });
    }

    private StatVO createDummyStatVO() {
        return StatVO.builder()
                .milestoneCount(4)
                .labelCount(3)
                .build();
    }

    private List<LabelDetailsVO> createDummyLabelDetailsVOs() {
        LabelDetailsVO tmp1 = LabelDetailsVO.builder()
                .id(1L)
                .name("tmp1")
                .description("label1")
                .backgroundColor("#FF000")
                .textColor("dark")
                .build();
        LabelDetailsVO tmp2 = LabelDetailsVO.builder()
                .id(2L)
                .name("tmp2")
                .description("label2")
                .backgroundColor("#FF000")
                .textColor("dark")
                .build();
        LabelDetailsVO tmp3 = LabelDetailsVO.builder()
                .id(3L)
                .name("tmp3")
                .description("label3")
                .backgroundColor("#FF000")
                .textColor("dark")
                .build();

        return List.of(tmp1, tmp2, tmp3);
    }
}
