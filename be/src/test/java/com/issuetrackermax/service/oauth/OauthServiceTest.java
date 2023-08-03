package com.issuetrackermax.service.oauth;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.issuetrackermax.controller.auth.dto.response.JwtResponse;
import com.issuetrackermax.domain.IntegrationTestSupport;

public class OauthServiceTest extends IntegrationTestSupport {
	@Mock
	OauthService oauthService;

	@Test
	public void testLogin() {
		//given
		String providerName = "github";
		String code = "code";
		when(oauthService.login(providerName, code)).thenReturn(new JwtResponse("accessToken", "refreshToken"));
		//when
		JwtResponse jwtResponse = oauthService.login(providerName, code);

		//then
		assertThat(jwtResponse.getAccessToken()).isEqualTo("accessToken");
		assertThat(jwtResponse.getRefreshToken()).isEqualTo("refreshToken");
	}
}
