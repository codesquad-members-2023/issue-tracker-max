package com.issuetrackermax.domain.oauth.entity;

import java.util.Arrays;
import java.util.Map;

import com.issuetrackermax.controller.auth.dto.response.MemberProfileResponse;

public enum OauthAttributes {
	GITHUB("github") {
		@Override
		public MemberProfileResponse of(Map<String, Object> attributes) {
			return MemberProfileResponse.builder()
				.loginId((String)attributes.get("login"))
				.build();
		}
	};
	private final String providerName;

	OauthAttributes(String name) {
		this.providerName = name;
	}

	public static MemberProfileResponse extract(String providerName, Map<String, Object> attributes) {
		return Arrays.stream(values())
			.filter(provider -> providerName.equals(provider.providerName))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new)
			.of(attributes);
	}

	public abstract MemberProfileResponse of(Map<String, Object> attributes);
}
