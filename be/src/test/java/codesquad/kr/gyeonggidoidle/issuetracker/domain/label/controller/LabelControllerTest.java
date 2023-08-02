package codesquad.kr.gyeonggidoidle.issuetracker.domain.label.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import codesquad.kr.gyeonggidoidle.issuetracker.annotation.ControllerTest;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.LabelService;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.information.LabelDetailsInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.label.service.information.LabelPageInformation;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ControllerTest(LabelController.class)
public class LabelControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LabelService labelService;

    @DisplayName("라벨 관리페이지 정보를 담은 LabelPageInformation을 LabelPageResponse으로 변환한다.")
    @Test
    void testReadLabelPage() throws Exception {
        given(labelService.readLabelPage()).willReturn(createDummyLabelPageInformation());

        ResultActions resultActions = mockMvc.perform(get("/api/labels"));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.milestoneCount").value(10))
                .andExpect(jsonPath("$.labels.length()").value(2))
                .andExpect(jsonPath("$.labels.[0].name").value("feat"))
                .andExpect(jsonPath("$.labels.[1].textColor").value("##"));

    }

    private LabelPageInformation createDummyLabelPageInformation() {
        return LabelPageInformation.builder()
                .milestoneCount(10)
                .labelCount(2)
                .labelDetailsInformations(createDummyLabelDetailsInformation())
                .build();
    }

    private List<LabelDetailsInformation> createDummyLabelDetailsInformation() {
        LabelDetailsInformation tmp1 = LabelDetailsInformation.builder()
                .id(1L)
                .name("feat")
                .description("test")
                .backgroundColor("#")
                .textColor("#")
                .build();
        LabelDetailsInformation tmp2 = LabelDetailsInformation.builder()
                .id(2L)
                .name("bug")
                .description("test")
                .backgroundColor("##")
                .textColor("##")
                .build();
        return List.of(tmp1, tmp2);
    }

}
