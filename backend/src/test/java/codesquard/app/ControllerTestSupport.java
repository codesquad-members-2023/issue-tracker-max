package codesquard.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.comment.controller.CommentController;
import codesquard.app.comment.service.CommentService;
import codesquard.app.issue.controller.IssueController;
import codesquard.app.issue.service.IssueService;
import codesquard.app.label.controller.LabelController;
import codesquard.app.label.service.LabelService;
import codesquard.app.user.controller.UserRestController;
import codesquard.app.user.service.UserService;

@WebMvcTest(controllers = {
	CommentController.class,
	UserRestController.class,
	IssueController.class,
	LabelController.class
})
public abstract class ControllerTestSupport {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@MockBean
	protected IssueService issueService;

	@MockBean
	protected CommentService commentService;

	@MockBean
	protected LabelService labelService;

	@MockBean
	protected UserService userService;

}
