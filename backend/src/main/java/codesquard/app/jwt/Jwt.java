package codesquard.app.jwt;

import java.util.Date;

import javax.servlet.http.Cookie;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.ToString;

@JsonRootName("jwt")
@ToString
public class Jwt {
	private static final int REFRESH_TOKEN_MAXAGE = 60 * 60 * 24 * 30; // 1달

	@JsonProperty("accessToken")
	private String accessToken;
	@JsonProperty("refreshToken")
	private String refreshToken;

	@JsonIgnore
	private Date expireDateAccessToken;

	@JsonIgnore
	private Date expireDateRefreshToken;

	public Jwt() {
	}

	public Jwt(String accessToken, String refreshToken, Date expireDateAccessToken, Date expireDateRefreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expireDateAccessToken = expireDateAccessToken;
		this.expireDateRefreshToken = expireDateRefreshToken;
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

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public long getExpireDateRefreshToken() {
		return expireDateRefreshToken.getTime();
	}

	public long getExpireDateAccessToken() {
		return expireDateAccessToken.getTime();
	}
}
