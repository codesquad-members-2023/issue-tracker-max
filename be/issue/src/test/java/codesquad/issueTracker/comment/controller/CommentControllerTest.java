package codesquad.issueTracker.comment.controller;


import static codesquad.issueTracker.global.exception.SuccessCode.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import annotation.ControllerTest;
import codesquad.issueTracker.comment.dto.CommentRequestDto;
import codesquad.issueTracker.comment.dto.CommentResponseDto;
import codesquad.issueTracker.comment.fixture.CommentTestFixture;
import codesquad.issueTracker.comment.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@ControllerTest(CommentController.class)
class CommentControllerTest extends CommentTestFixture {

    private final Log log = LogFactory.getLog(CommentControllerTest.class);

    private List<CommentResponseDto> commentResponseDtosFixture;
    private CommentRequestDto commentRequestDtoFixture;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        commentResponseDtosFixture = dummyCommentResponseDtos();
        commentRequestDtoFixture = dummyCommentRequestDto(1);
    }

    @Test
    @DisplayName("모든 댓글 목록들을 반환한다.")
    public void getComments() throws Exception {
        //given
        given(commentService.getComments(any())).willReturn(commentResponseDtosFixture);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/issues/{issueId}/comments", 1L));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(SUCCESS.getStatus().getReasonPhrase()))
                .andExpect(jsonPath("$.message[0].id").value(commentResponseDtosFixture.get(0).getId()))
                .andExpect(jsonPath("$.message[0].createdAt").value(commentResponseDtosFixture.get(0).getCreatedAt().toString()))
                .andExpect(jsonPath("$.message[0].content").value(commentResponseDtosFixture.get(0).getContent()))
                .andExpect(jsonPath("$.message[0].writer.name").value(commentResponseDtosFixture.get(0).getWriter().getName()))
                .andExpect(jsonPath("$.message[0].writer.profileImg").value(commentResponseDtosFixture.get(0).getWriter().getProfileImg()));
    }

    @Test
    @DisplayName("이슈 댓글을 작성한다.")
    public void save() throws Exception {
        //given
        given(commentService.save(any(), any(), any())).willReturn(1L);

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/issues/{issueId}/comments", 1L)
                .content(objectMapper.writeValueAsString(commentRequestDtoFixture))
                .contentType(MediaType.APPLICATION_JSON)
                .requestAttr("userId", 1L));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(SUCCESS.getStatus().getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));
    }

    @Test
    @DisplayName("이슈 댓글을 수정한다.")
    public void modify() throws Exception {
        //given
        given(commentService.modify(any(), any())).willReturn(1L);

        //when
        ResultActions resultActions = mockMvc.perform(patch("/api/issues/comments/{commentId}", 1L)
                .content(objectMapper.writeValueAsString(commentRequestDtoFixture))
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(SUCCESS.getStatus().getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));
    }

    @Test
    @DisplayName("이슈 댓글을 삭제한다.")
    public void delete() throws Exception {
        //given
        given(commentService.delete(any())).willReturn(1L);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/issues/comments/{commentId}", 1L));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(SUCCESS.getStatus().getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));
    }
}
