package org.presents.issuetracker.comment;

import org.presents.issuetracker.annotation.ControllerTest;
import org.presents.issuetracker.comment.controller.CommentController;
import org.presents.issuetracker.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@ControllerTest(CommentController.class)
public class CommentControllerTest {
    // 예시
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;
}
