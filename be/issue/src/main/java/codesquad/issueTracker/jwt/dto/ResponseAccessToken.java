package codesquad.issueTracker.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseAccessToken {
	private String accessToken;
}
