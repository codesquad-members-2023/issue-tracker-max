package codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.controller;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.ControllerTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.MilestoneService;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.information.MilestoneDetailsInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.milestone.service.information.MilestonePageInformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(MilestoneController.class)
class MilestoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MilestoneService milestoneService;

    @DisplayName("열린 마일스톤에 관한 MilestonePageInformation을 MilestonePageResponse로 변환한다.")
    @Test
    void transformToMileStonePageResponse() throws Exception {
        //given
        given(milestoneService.readOpenMilestonePage()).willReturn(createDummyMilestonePageInformation());

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/milestones/open"));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.closeMilestoneCount").value(0),
                        jsonPath("$.labelCount").value(6),
                        jsonPath("$.milestones.length()").value(2),
                        jsonPath("$.milestones.[0].dueDate").value("2024-12-25"),
                        jsonPath("$.milestones.[1].name").value("tmp2")
                );
    }

    private MilestonePageInformation createDummyMilestonePageInformation() {
        return MilestonePageInformation.builder()
                .openMilestoneCount(2)
                .closeMilestoneCount(0)
                .labelCount(6)
                .milestoneDetailsInformations(createDummyMilestoneDetailsInformations())
                .build();
    }

    private List<MilestoneDetailsInformation> createDummyMilestoneDetailsInformations() {
        MilestoneDetailsInformation tmp1 = MilestoneDetailsInformation.builder()
                .id(1L)
                .name("tmp1")
                .description("test")
                .dueDate(LocalDate.of(2024, 12, 25))
                .openIssueCount(1)
                .closedIssuesCount(2)
                .build();
        MilestoneDetailsInformation tmp2 = MilestoneDetailsInformation.builder()
                .id(2L)
                .name("tmp2")
                .description("test2")
                .dueDate(LocalDate.now())
                .openIssueCount(3)
                .closedIssuesCount(4)
                .build();
        return List.of(tmp1, tmp2);
    }
}
