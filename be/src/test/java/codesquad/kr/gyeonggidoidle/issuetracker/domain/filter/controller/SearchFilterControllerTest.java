package codesquad.kr.gyeonggidoidle.issuetracker.domain.filter.controller;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.ControllerTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.filter.service.FilterService;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.filter.service.information.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(FilterController.class)
class SearchFilterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilterService filterService;

    @DisplayName("메인 화면의 필터 내용을 담은 FilterListInformation을 FilterListResponse으로 변환한다.")
    @Test
    void readFilters() throws Exception {
        //given
        given(filterService.getMainFilter()).willReturn(createDummyFilterListInformation());

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/filters/main"));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.assignee.length()").value(3),
                        jsonPath("$.author.length()").value(2),
                        jsonPath("$.author.[0].name").value("a"),
                        jsonPath("$.label.length()").value(1),
                        jsonPath("$.milestone.length()").value(0)
                );
    }

    @DisplayName("이슈 화면의 필터 내용을 담은 FilterListInformation을 FilterListResponse으로 변환한다.")
    @Test
    void readFiltersFromIssue() throws Exception {
        //given
        given(filterService.getDetailFilter()).willReturn(createDummyFilterListInformationByIssue());

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/filters/detail"));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.assignee.length()").value(3),
                        jsonPath("$.author.length()").value(0),
                        jsonPath("$.label.length()").value(1),
                        jsonPath("$.milestone.length()").value(0)
                );
    }

    private FilterInformation createDummyFilterListInformation() {
        return FilterInformation.builder()
                .assigneeFilterInformations(createDummyAssigneeFilterInformations())
                .authorFilterInformations(createDummyAuthorFilterInformations())
                .labelFilterInformations(createDummyLabelFilterInformations())
                .milestoneFilterInformations(createDummyMilestoneFilterInformations())
                .build();
    }

    private FilterInformation createDummyFilterListInformationByIssue() {
        return FilterInformation.builder()
                .assigneeFilterInformations(createDummyAssigneeFilterInformations())
                .authorFilterInformations(Collections.emptyList())
                .labelFilterInformations(createDummyLabelFilterInformations())
                .milestoneFilterInformations(createDummyMilestoneFilterInformations())
                .build();
    }

    private List<AssigneeFilterInformation> createDummyAssigneeFilterInformations() {
        AssigneeFilterInformation tmp1 = AssigneeFilterInformation.builder()
                .id(0L)
                .name("담당자가 없는 이슈")
                .profile("")
                .build();
        AssigneeFilterInformation tmp2 = AssigneeFilterInformation.builder()
                .id(1L)
                .name("a")
                .profile("aa")
                .build();
        AssigneeFilterInformation tmp3 = AssigneeFilterInformation.builder()
                .id(2L)
                .name("b")
                .profile("bb")
                .build();
        return List.of(tmp1, tmp2, tmp3);
    }

    private List<AuthorFilterInformation> createDummyAuthorFilterInformations() {
        AuthorFilterInformation tmp1 = AuthorFilterInformation.builder()
                .id(1L)
                .name("a")
                .profile("aa")
                .build();
        AuthorFilterInformation tmp2 = AuthorFilterInformation.builder()
                .id(2L)
                .name("b")
                .profile("bb")
                .build();
        return List.of(tmp1, tmp2);
    }

    private List<LabelFilterInformation> createDummyLabelFilterInformations() {
        LabelFilterInformation tmp1 = LabelFilterInformation.builder()
                .id(1L)
                .name("label")
                .backgroundColor("#FF")
                .textColor("color")
                .build();
        return List.of(tmp1);
    }

    private List<MilestoneFilterInformation> createDummyMilestoneFilterInformations() {
        return List.of();
    }
}
