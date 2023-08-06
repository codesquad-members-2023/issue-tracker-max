package com.issuetrackermax.service.oauth;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.issuetrackermax.controller.auth.dto.response.JwtResponse;
import com.issuetrackermax.controller.auth.dto.response.MemberProfileResponse;
import com.issuetrackermax.controller.auth.dto.response.OauthTokenResponse;
import com.issuetrackermax.domain.jwt.JwtRepository;
import com.issuetrackermax.domain.jwt.entity.Jwt;
import com.issuetrackermax.domain.member.entity.Member;
import com.issuetrackermax.domain.oauth.InMemoryProviderRepository;
import com.issuetrackermax.domain.oauth.entity.OauthAttributes;
import com.issuetrackermax.service.jwt.JwtProvider;
import com.issuetrackermax.service.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OauthService {
	private static final String MEMBER_ID = "memberId";
	private static final String TOKEN_REQUEST_CODE = "code";
	private static final String GRANT_TYPE = "grant_Type";
	private static final String AUTHORIZATION_CODE = "authorization_code";
	private static final String REDIRECT_URI = "redirect_uri";
	private final InMemoryProviderRepository inMemoryProviderRepository;
	private final MemberService memberService;
	private final JwtProvider jwtProvider;
	private final JwtRepository jwtRepository;

	@Transactional
	public JwtResponse login(String providerName, String code) {
		OauthProvider provider = inMemoryProviderRepository.findByProviderName(providerName);
		OauthTokenResponse tokenResponse = getToken(code, provider);
		MemberProfileResponse memberProfileResponse = getMemberProfileResponse(providerName, tokenResponse, provider);
		Member member = memberService.registerOauthMember(memberProfileResponse);
		Jwt jwt = jwtProvider.createJwt(Map.of(MEMBER_ID, String.valueOf(member.getId())));
		jwtRepository.saveRefreshToken(jwt.getRefreshToken(), member.getId());
		return JwtResponse.from(jwt);
	}

	private MemberProfileResponse getMemberProfileResponse(String providerName, OauthTokenResponse tokenResponse,
		OauthProvider provider) {
		Map<String, Object> memberAttributes = getMemberAttributes(provider, tokenResponse);
		return OauthAttributes.extract(providerName, memberAttributes);
	}

	private Map<String, Object> getMemberAttributes(OauthProvider provider, OauthTokenResponse tokenResponse) {
		return WebClient.create()
			.get()
			.uri(provider.getUserInfoUrl())
			.headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
			})
			.block();
	}

	private OauthTokenResponse getToken(String code, OauthProvider provider) {
		return WebClient.create()
			.post()
			.uri(provider.getTokenUrl())
			.headers(header -> {
				header.setBasicAuth(provider.getClientId(), provider.getClientSecret());
				header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
			})
			.bodyValue(tokenRequest(code, provider))
			.retrieve()
			.bodyToMono(OauthTokenResponse.class)
			.block();
	}

	private MultiValueMap<String, String> tokenRequest(String code, OauthProvider provider) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add(TOKEN_REQUEST_CODE, code);
		formData.add(GRANT_TYPE, AUTHORIZATION_CODE);
		formData.add(REDIRECT_URI, provider.getRedirectUrl());
		return formData;
	}
}
