package codesquard.app.oauth.properties;

import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.oauth.OauthProvider;

class OauthPropertiesTest extends IntegrationTestSupport {

	private static final Logger logger = LoggerFactory.getLogger(OauthPropertiesTest.class);

	@Autowired
	private OauthProperties oauthProperties;

	@Test
	@DisplayName("application.yml에 저장된 oauth 설정들이 객체에 저장된다")
	public void createOauthProviderMap() {
		// when
		Map<String, OauthProvider> providerMap = oauthProperties.createOauthProviderMap();
		OauthProvider githubOauthProvider = providerMap.get("github");
		// then
		Assertions.assertThat(githubOauthProvider).isNotNull();
	}
}
