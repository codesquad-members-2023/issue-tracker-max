package codesquad.issueTracker.jwt.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Token {
	private Long id;
	private Long userId;
	private String refreshToken;

	@Builder
	public Token(Long id, Long userId, String refreshToken) {
		this.id = id;
		this.userId = userId;
		this.refreshToken = refreshToken;
	}
}
