package codesquad.issueTracker.oauth.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@ConfigurationProperties(prefix = "oauth2")
public class OAuthProperties {

	private final Map<String, Client> client = new HashMap<>();
	private final Map<String, Provider> provider = new HashMap();

	@Getter
	@Setter
	public static class Client {
		private String clientId;
		private String clientSecret;
		private String redirectUrl;
	}

	@Getter
	@Setter
	public static class Provider {
		private String tokenUrl;
		private String userInfoUrl;
	}
}
