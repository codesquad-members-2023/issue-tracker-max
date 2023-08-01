package codesquard.app.label_milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.label.controller.LabelController;
import codesquard.app.label.service.LabelService;
import codesquard.app.milestone.controller.MilestoneController;
import codesquard.app.milestone.service.MilestoneService;

@WebMvcTest(controllers = {
	MilestoneController.class,
	LabelController.class
})
public class ControllerTestSupport {
	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@MockBean
	protected MilestoneService milestoneService;

	@MockBean
	protected LabelService labelService;
}
