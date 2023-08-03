package codesquard.app.jwt;

import javax.servlet.http.Cookie;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Jwt {
	private static final int REFRESH_TOKEN_MAXAGE = 60 * 60 * 24 * 30; // 1달

	@JsonProperty("accessToken")
	private final String accessToken;
	@JsonProperty("refreshToken")
	private final String refreshToken;

	public Jwt(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public Cookie createRefreshTokenCookie() {
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setMaxAge(REFRESH_TOKEN_MAXAGE); // 1달
		return refreshTokenCookie;
	}

	public String createAccessTokenHeaderValue() {
		return "Bearer " + accessToken;
	}

	public MapSqlParameterSource createParamSource() {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("accessToken", accessToken);
		parameterSource.addValue("refreshToken", refreshToken);
		return parameterSource;
	}
}
