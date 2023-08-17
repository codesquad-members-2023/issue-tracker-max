package codesquard.app.oauth.fixture;

import javax.servlet.http.Cookie;

import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.jwt.Jwt;
import codesquard.app.oauth.profile.UserProfile;
import codesquard.app.oauth.service.response.OauthAccessTokenResponse;
import codesquard.app.oauth.service.response.OauthLoginServiceResponse;

public class FixedAuthenticateUserFactory {

	private static final Long ID = 1L;
	private static final String LOGIN_ID = "hong1234";
	private static final String EMAIL = "hong1234@gmail.com";
	private static final String ACCESS_TOKEN = "accessToken";
	private static final String REFRESH_TOKEN = "refreshToken";
	private static final String SCOPE = "login";
	private static final String TOKEN_TYPE = "Bearer";

	public static AuthenticateUser authenticateUser() {
		return new AuthenticateUser(ID, LOGIN_ID, EMAIL, null);
	}

	public static Jwt jwt() {
		return new Jwt(ACCESS_TOKEN, REFRESH_TOKEN, null, null);
	}

	public static Cookie refreshTokenCookie() {
		return new Cookie(REFRESH_TOKEN, REFRESH_TOKEN);
	}

	public static OauthLoginServiceResponse oauthLoginServiceResponse() {
		AuthenticateUser user = authenticateUser();
		Jwt jwt = jwt();
		return new OauthLoginServiceResponse(user, jwt, TOKEN_TYPE);
	}

	public static OauthAccessTokenResponse oauthAccessTokenResponse() {
		return new OauthAccessTokenResponse(ACCESS_TOKEN, SCOPE, TOKEN_TYPE);
	}

	public static UserProfile userProfile() {
		return new UserProfile(LOGIN_ID, EMAIL, null);
	}

}
