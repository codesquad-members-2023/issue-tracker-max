package codesquard.app.user.password;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import codesquard.app.IntegrationTestSupport;

class PasswordEncoderTest extends IntegrationTestSupport {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("영어와 숫자로 이루어진 패스워드 문자열이 주어지고 암호화를 요청하면 암호화가 된다")
	public void encrypt() {
		// given
		String password = "hong1234";
		// when
		String encrypt = passwordEncoder.encrypt(password);
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(encrypt).isEqualTo("x07g//r+itCtbw9diTdmHA==");
			softAssertions.assertAll();
		});
	}

}
