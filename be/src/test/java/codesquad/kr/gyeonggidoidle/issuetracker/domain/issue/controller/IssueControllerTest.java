package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.ControllerTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.IssueService;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.SearchInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.service.information.IssueInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.information.LabelInformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(IssueController.class)
class IssueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssueService issueService;

    @DisplayName("열린 이슈에 관한 FilterInformation을 FilterResponse로 변환한다.")
    @Test
    void readOpenIssues() throws Exception {
        //given
        given(issueService.findIssuesBySearchFilter(anyString())).willReturn(createDummyFilterInformation());

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/issues?q=is%3Aopen"));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.openIssueCount").value(3),
                        jsonPath("$.milestoneCount").value(2),
                        jsonPath("$.issues.length()").value(3),
                        jsonPath("$.issues.[1].title").value("제목 2"),
                        jsonPath("$.issues.[2].labels.length()").value(0)
                );
    }

    @DisplayName("닫힌 이슈에 관한 FilterInformation을 FilterResponse로 변환한다.")
    @Test
    void readClosedIssues() throws Exception {
        //given
        given(issueService.findIssuesBySearchFilter(anyString())).willReturn(createDummyFilterInformation());

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/issues?q=is%3Aclosed"));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.closedIssueCount").value(3),
                        jsonPath("$.labelCount").value(4),
                        jsonPath("$.issues.length()").value(3),
                        jsonPath("$.issues.[0].author").value("작성자 1"),
                        jsonPath("$.issues.[1].assigneeProfiles.[0]").value("담당자 3")
                );
    }

    private SearchInformation createDummyFilterInformation() {
        return SearchInformation.builder()
                .openIssueCount(3)
                .closedIssueCount(3)
                .milestoneCount(2)
                .labelCount(4)
                .issueInformations(createDummyIssueInformations())
                .build();
    }

    private List<IssueInformation> createDummyIssueInformations() {
        List<LabelInformation> labelInformations = createDummyLabelInformations();
        IssueInformation issueInformation1 = IssueInformation.builder()
                .id(1L)
                .author("작성자 1")
                .title("제목 1")
                .milestone("마일스톤 1")
                .assigneeProfiles(List.of("담당자 1", "담당자 2"))
                .labelInformations(labelInformations.subList(0, 3))
                .createdAt(LocalDateTime.now())
                .build();
        IssueInformation issueInformation2 = IssueInformation.builder()
                .id(2L)
                .author("작성자 2")
                .title("제목 2")
                .milestone("마일스톤 2")
                .assigneeProfiles(List.of("담당자 3"))
                .labelInformations(labelInformations.subList(3, 4))
                .createdAt(LocalDateTime.now())
                .build();
        IssueInformation issueInformation3 = IssueInformation.builder()
                .id(3L)
                .author("작성자 3")
                .title("제목 3")
                .milestone("마일스톤 1")
                .assigneeProfiles(List.of())
                .labelInformations(Collections.emptyList())
                .createdAt(LocalDateTime.now())
                .build();

        return List.of(issueInformation1, issueInformation2, issueInformation3);
    }

    private List<LabelInformation> createDummyLabelInformations() {
        LabelInformation labelInformation1 = LabelInformation.builder()
                .name("라벨 1")
                .backgroundColor("배경색 1")
                .textColor("글자색 1")
                .build();
        LabelInformation labelInformation2 = LabelInformation.builder()
                .name("라벨 2")
                .backgroundColor("배경색 2")
                .textColor("글자색 2")
                .build();
        LabelInformation labelInformation3 = LabelInformation.builder()
                .name("라벨 3")
                .backgroundColor("배경색 3")
                .textColor("글자색 3")
                .build();
        LabelInformation labelInformation4 = LabelInformation.builder()
                .name("라벨 4")
                .backgroundColor("배경색 4")
                .textColor("글자색 4")
                .build();
        LabelInformation labelInformation5 = LabelInformation.builder()
                .name("라벨 5")
                .backgroundColor("배경색 5")
                .textColor("글자색 5")
                .build();

        return List.of(labelInformation1, labelInformation2, labelInformation3, labelInformation4, labelInformation5);
    }
}
