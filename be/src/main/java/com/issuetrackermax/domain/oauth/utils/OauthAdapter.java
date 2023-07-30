package com.issuetrackermax.domain.oauth.utils;

import java.util.HashMap;
import java.util.Map;

import com.issuetrackermax.domain.oauth.entity.OauthProperties;
import com.issuetrackermax.service.oauth.OauthProvider;

public class OauthAdapter {

	private OauthAdapter() {
	}

	// OauthProperties를 OauthProvider로 변환해준다.
	public static Map<String, OauthProvider> getOauthProviders(OauthProperties properties) {
		Map<String, OauthProvider> oauthProvider = new HashMap<>();

		properties.getUser().forEach((key, value) -> oauthProvider.put(key,
			OauthProvider.createOauthProvider(value, properties.getProvider().get(key))));
		return oauthProvider;
	}
}
