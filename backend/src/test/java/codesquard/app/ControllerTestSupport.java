package codesquard.app;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.api.errors.handler.GlobalExceptionHandler;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.authenticate_user.resolver.LoginUserArgumentResolver;
import codesquard.app.authenticate_user.service.AuthenticateUserService;
import codesquard.app.comment.controller.CommentController;
import codesquard.app.comment.service.CommentService;
import codesquard.app.issue.controller.IssueController;
import codesquard.app.issue.service.IssueQueryService;
import codesquard.app.issue.service.IssueService;
import codesquard.app.jwt.JwtProvider;
import codesquard.app.label.controller.LabelController;
import codesquard.app.label.service.LabelService;
import codesquard.app.oauth.service.OauthService;
import codesquard.app.user.controller.UserRestController;
import codesquard.app.user.service.UserQueryService;
import codesquard.app.user.service.UserService;
import codesquard.app.user_reaction.controller.UserReactionController;
import codesquard.app.user_reaction.service.UserReactionService;

@WebMvcTest(controllers = {
	CommentController.class,
	UserRestController.class,
	IssueController.class,
	LabelController.class,
	UserReactionController.class
})
@Import(value = {JwtProvider.class})
public abstract class ControllerTestSupport {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected JwtProvider jwtProvider;

	@Autowired
	protected ObjectMapper objectMapper;

	@MockBean
	protected IssueService issueService;

	@MockBean
	protected IssueQueryService issueQueryService;

	@MockBean
	protected CommentService commentService;

	@MockBean
	protected LabelService labelService;

	@MockBean
	protected UserService userService;

	@MockBean
	protected UserQueryService userQueryService;

	@MockBean
	protected AuthenticateUserService authenticateUserService;

	@MockBean
	protected UserReactionService userReactionService;

	@MockBean
	protected OauthService oauthService;

	@Mock
	protected LoginUserArgumentResolver loginUserArgumentResolver;

	protected void mockingAuthenticateUser() {
		mockMvc = MockMvcBuilders.standaloneSetup(new IssueController(issueService, issueQueryService))
			.setControllerAdvice(new GlobalExceptionHandler())
			.setCustomArgumentResolvers(loginUserArgumentResolver)
			.build();

		AuthenticateUser authenticateUser = new AuthenticateUser(1L, "wis123", "wis123@naver.com", null);
		when(loginUserArgumentResolver.supportsParameter(any()))
			.thenReturn(true);
		when(loginUserArgumentResolver.resolveArgument(any(), any(), any(), any()))
			.thenReturn(authenticateUser);
	}
}
