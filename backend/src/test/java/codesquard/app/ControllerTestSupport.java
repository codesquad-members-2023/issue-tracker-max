package codesquard.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.issue.controller.IssueController;
import codesquard.app.issue.service.IssueService;
import codesquard.app.milestone.controller.MilestoneRestController;
import codesquard.app.milestone.service.MilestoneService;
import codesquard.app.user.controller.UserRestController;
import codesquard.app.user.service.UserService;

@WebMvcTest(controllers = {
	IssueController.class,
	UserRestController.class,
	MilestoneRestController.class
})
public abstract class ControllerTestSupport {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@MockBean
	protected IssueService issueService;
	@MockBean
	protected UserService userService;
	@MockBean
	protected MilestoneService milestoneService;
}
