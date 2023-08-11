package codesquard.app.oauth;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import codesquard.app.IntegrationTestSupport;

class InMemoryProviderRepositoryTest extends IntegrationTestSupport {

	private static final Logger logger = LoggerFactory.getLogger(InMemoryProviderRepositoryTest.class);

	@Autowired
	private InMemoryProviderRepository inMemoryProviderRepository;

	@Test
	@DisplayName("자동주입된 InMemoryProviderRepository에 깃허브 프로퍼티가 저장되어 있다")
	public void constructor() {
		// given

		// when
		OauthProvider github = inMemoryProviderRepository.findByProviderName("github");
		// then
		logger.info("github : {}", github);
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(github)
				.extracting("userProperties")
				.extracting("clientId")
				.isNotNull();
		});
	}

}
