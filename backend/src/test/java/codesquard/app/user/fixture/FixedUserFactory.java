package codesquard.app.user.fixture;

import codesquard.app.user.entity.User;
import codesquard.app.user.service.request.UserLoginServiceRequest;
import codesquard.app.user.service.request.UserSaveServiceRequest;

public class FixedUserFactory {

	private static final String LOGIN_ID = "hong1234";
	private static final String EMAIL = "hong1234@gmail.com";
	private static final String PASSWORD = "hong1234hong1234";
	private static final String NOT_MATCHED_PASSWORD = "lee1234lee1234";

	public static User fixedUser() {
		return new User(null, LOGIN_ID, EMAIL, PASSWORD, null);
	}

	public static UserSaveServiceRequest userSaveServiceRequest() {
		return new UserSaveServiceRequest(LOGIN_ID, EMAIL, PASSWORD, PASSWORD, null);
	}

	public static UserSaveServiceRequest passwordNotMatchedUserSaveServiceRequest() {
		return new UserSaveServiceRequest(LOGIN_ID, EMAIL, PASSWORD, NOT_MATCHED_PASSWORD, null);
	}

	public static UserLoginServiceRequest userLoginServiceRequest(){
		return new UserLoginServiceRequest(LOGIN_ID, PASSWORD);
	}
}
