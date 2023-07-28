package com.issuetrackermax.domain.oAuth.service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.issuetrackermax.controller.auth.dto.response.JwtResponse;
import com.issuetrackermax.controller.auth.dto.response.MemberProfileResponse;
import com.issuetrackermax.controller.auth.dto.response.OauthTokenResponse;
import com.issuetrackermax.domain.jwt.service.JwtProvider;
import com.issuetrackermax.domain.member.Entity.Member;
import com.issuetrackermax.domain.member.MemberRepository;
import com.issuetrackermax.domain.oAuth.InMemoryProviderRepository;
import com.issuetrackermax.domain.oAuth.entity.OauthAttributes;

@Service
public class OauthService {

	private final InMemoryProviderRepository inMemoryProviderRepository;
	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;

	public OauthService(InMemoryProviderRepository inMemoryProviderRepository, MemberRepository memberRepository,
		JwtProvider jwtProvider) {
		this.inMemoryProviderRepository = inMemoryProviderRepository;
		this.memberRepository = memberRepository;
		this.jwtProvider = jwtProvider;
	}

	public JwtResponse login(String providerName, String code) {
		// 프론트에서 넘어온 provider 이름을 통해 InMemoryProviderRepository에서 OauthProvider 가져오기
		OauthProvider provider = inMemoryProviderRepository.findByProviderName(providerName);

		// access token 가져오기
		OauthTokenResponse tokenResponse = getToken(code, provider);

		// 유저 정보 가져오기
		MemberProfileResponse memberProfileResponse = getMemberProfileResponse(providerName, tokenResponse, provider);

		// 유저 DB에 저장
		Member member = saveOrUpdate(memberProfileResponse);

		// 우리 애플리케이션의 JWT 토큰 만들기
		return JwtResponse.from(jwtProvider.createJwt(Map.of("memberId", String.valueOf(member.getId()))));
	}

	private Member saveOrUpdate(MemberProfileResponse memberProfileResponse) {
		try {
			return memberRepository.findByMemberEmail(memberProfileResponse.getLoginId()).get();
		} catch (Exception e) {
			memberRepository.save(memberProfileResponse.toMember());
			return memberRepository.findByMemberEmail(memberProfileResponse.getLoginId()).get();
		}
	}

	private MemberProfileResponse getMemberProfileResponse(String providerName, OauthTokenResponse tokenResponse,
		OauthProvider provider) {
		Map<String, Object> memberAttributes = getMemberAttributes(provider, tokenResponse);
		//유저 정보(map)를 통해 UserProfile 만들기
		return OauthAttributes.extract(providerName, memberAttributes);
	}

	// OAuth 서버에서 유저 정보 map으로 가져오기
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
		formData.add("code", code);
		formData.add("grant_type", "authorization_code");
		formData.add("redirect_uri", provider.getRedirectUrl());
		return formData;
	}
}
