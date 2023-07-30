package com.issuetrackermax.service.oauth;

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
import com.issuetrackermax.domain.oauth.InMemoryProviderRepository;
import com.issuetrackermax.domain.oauth.entity.OauthAttributes;

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
		OauthProvider provider = inMemoryProviderRepository.findByProviderName(providerName);

		OauthTokenResponse tokenResponse = getToken(code, provider);

		MemberProfileResponse memberProfileResponse = getMemberProfileResponse(providerName, tokenResponse, provider);

		Member member = saveOrUpdate(memberProfileResponse);

		return JwtResponse.from(jwtProvider.createJwt(Map.of("memberId", String.valueOf(member.getId()))));
	}

	private Member saveOrUpdate(MemberProfileResponse memberProfileResponse) {
		try {
			return memberRepository.findByMemberLoginId(memberProfileResponse.getLoginId()).get();
		} catch (Exception e) {
			memberRepository.save(memberProfileResponse.toMember());
			return memberRepository.findByMemberLoginId(memberProfileResponse.getLoginId()).get();
		}
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
		formData.add("code", code);
		formData.add("grant_type", "authorization_code");
		formData.add("redirect_uri", provider.getRedirectUrl());
		return formData;
	}
}
