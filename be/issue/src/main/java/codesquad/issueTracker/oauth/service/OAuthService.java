package codesquad.issueTracker.oauth.service;

import codesquad.issueTracker.jwt.domain.Jwt;
import codesquad.issueTracker.jwt.domain.UserProfile;
import codesquad.issueTracker.jwt.dto.OauthTokenResponse;
import codesquad.issueTracker.jwt.util.InMemoryProviderRepository;
import codesquad.issueTracker.jwt.util.JwtProvider;
import codesquad.issueTracker.oauth.domain.OauthAttributes;
import codesquad.issueTracker.oauth.util.OauthProvider;
import codesquad.issueTracker.user.domain.User;
import codesquad.issueTracker.user.dto.LoginResponseDto;
import codesquad.issueTracker.user.service.UserService;
import codesquad.issueTracker.user.service.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuthService {
    private final UserService userService;
    private final UserValidator userValidator;
    private final JwtProvider jwtProvider;
    private final InMemoryProviderRepository inMemoryProviderRepository;

    @Transactional
    public LoginResponseDto oauthLogin(String providerName, String code) {
        OauthProvider provider = findByProviderName(providerName);
        OauthTokenResponse tokenResponse = getToken(code, provider);
        User user = getUserProfile(providerName, tokenResponse, provider).toUser(providerName);

        User findUser = userService.findExistedOrInsertedUser(user);

        userValidator.validateLoginType(user.getLoginType(), findUser.getLoginType());

        Jwt jwt = jwtProvider.createJwt(Map.of("userId", findUser.getId()));
        userService.insertOrUpdateToken(findUser.getId(), jwt);

        return LoginResponseDto.builder()
                .userId(findUser.getId())
                .userName(findUser.getName())
                .accessToken(jwt.getAccessToken())
                .refreshToken(jwt.getRefreshToken())
                .profileImgUrl(user.getProfileImg())
                .build();
    }

    public OauthTokenResponse getToken(String code, OauthProvider provider) {
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

    public UserProfile getUserProfile(String providerName, OauthTokenResponse tokenResponse, OauthProvider provider) {
        Map<String, Object> userAttributes = getUserAttributes(provider, tokenResponse);
        if (providerName.equals("github") && userAttributes.get("email") == null) {
            List<Map<String, Object>> temp = getUserEmail(tokenResponse);
            userAttributes.put("email", temp.get(0).get("email"));
        }
        return OauthAttributes.extract(providerName, userAttributes);
    }

    public Map<String, Object> getUserAttributes(OauthProvider provider, OauthTokenResponse tokenResponse) {
        return WebClient.create()
                .get()
                .uri(provider.getUserInfoUrl())
                .headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }

    public List<Map<String, Object>> getUserEmail(OauthTokenResponse tokenResponse) {
        return WebClient.create()
                .get()
                .uri("https://api.github.com/user/emails")
                .headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {
                })
                .block();
    }

    public OauthProvider findByProviderName(String name) {
        return inMemoryProviderRepository.findByProviderName(name);
    }
}

