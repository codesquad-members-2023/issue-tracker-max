package codesquard.app.user.fixture;

import codesquard.app.user.controller.request.UserSaveRequest;
import codesquard.app.user.entity.User;
import codesquard.app.user.service.request.UserLoginServiceRequest;
import codesquard.app.user.service.request.UserSaveServiceRequest;
import codesquard.app.user.service.response.UserSaveServiceResponse;

public class FixedUserFactory {

	private static final String LOGIN_ID = "hong1234";
	private static final String EMAIL = "hong1234@gmail.com";
	private static final String PASSWORD = "hong1234hong1234";
	private static final String NOT_MATCHED_PASSWORD = "lee1234lee1234";

	public static User fixedUser() {
		return new User(null, LOGIN_ID, EMAIL, PASSWORD, null);
	}

	public static UserSaveRequest userSaveRequest() {
		return new UserSaveRequest(LOGIN_ID, EMAIL, PASSWORD, PASSWORD);
	}

	public static UserSaveRequest emptyLoginIdUserSaveRequest() {
		return new UserSaveRequest("", EMAIL, PASSWORD, PASSWORD);
	}

	public static UserSaveServiceRequest userSaveServiceRequest() {
		return new UserSaveServiceRequest(LOGIN_ID, EMAIL, PASSWORD, PASSWORD, null);
	}

	public static UserSaveServiceRequest userSaveServiceRequest(String loginId) {
		return new UserSaveServiceRequest(loginId, EMAIL, PASSWORD, PASSWORD, null);
	}

	public static UserSaveRequest invalidEmailUserSaveRequest(String email) {
		return new UserSaveRequest(LOGIN_ID, email, PASSWORD, PASSWORD);
	}

	public static UserSaveRequest invalidPasswordUserSaveRequest(String password) {
		return new UserSaveRequest(LOGIN_ID, EMAIL, password, PASSWORD);
	}

	public static UserSaveRequest invalidPasswordAndPasswordConfirmUserSaveRequest(String password,
		String passwordConfirm) {
		return new UserSaveRequest(LOGIN_ID, EMAIL, password, passwordConfirm);
	}

	public static UserSaveRequest invalidLoginIdAndEmailUserSaveRequest(String loginId, String email) {
		return new UserSaveRequest(loginId, email, PASSWORD, PASSWORD);
	}

	public static UserSaveRequest passwordNotMatchedUserSaveRequest() {
		return new UserSaveRequest(LOGIN_ID, EMAIL, PASSWORD, NOT_MATCHED_PASSWORD);
	}

	public static UserSaveServiceRequest passwordNotMatchedUserSaveServiceRequest() {
		return new UserSaveServiceRequest(LOGIN_ID, EMAIL, PASSWORD, NOT_MATCHED_PASSWORD, null);
	}

	public static UserSaveServiceResponse userSaveServiceResponse() {
		return new UserSaveServiceResponse(1L, LOGIN_ID, EMAIL);
	}

	public static UserLoginServiceRequest userLoginServiceRequest() {
		return new UserLoginServiceRequest(LOGIN_ID, PASSWORD);
	}

}
