package codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.service;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.controller.response.OauthTokenResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.entity.Jwt;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.entity.JwtProvider;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.OauthAttributes;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.entity.OauthProvider;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.entity.UserProfile;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.repository.InMemoryProviderRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.repository.JwtTokenRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.service.information.JwtLoginInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.Member;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.MemberRepository;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class OauthService {

    private final InMemoryProviderRepository inMemoryProviderRepository;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final JwtTokenRepository jwtTokenRepository;

    public JwtLoginInformation login(String providerName, String code) {
        // 프론트에서 넘어온 provider 이름을 통해 InMemoryProviderRepository에서 OauthProvider 가져오기
        OauthProvider provider = inMemoryProviderRepository.findByProviderName(providerName);
        // access token 가져오기
        OauthTokenResponse tokenResponse = getToken(code, provider);
        // 유저 정보 가져오기
        UserProfile userProfile = getUserProfile(providerName, tokenResponse, provider);
        // 유저 DB에 있는지 확인 후 없으면 저장
        Member member = findOrCreatemember(userProfile);
        // 우리 애플리케이션의 JWT 토큰 만들기
        Jwt jwt = jwtProvider.createJwt(Map.of("memberId", member.getId()));
        jwtTokenRepository.saveRefreshToken(jwt.getRefreshToken(), member.getId());
        return JwtLoginInformation.from(member, jwt);
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

    private UserProfile getUserProfile(String providerName, OauthTokenResponse tokenResponse, OauthProvider provider) {
        Map<String, Object> userAttributes = getUserAttributes(provider, tokenResponse);
        return OauthAttributes.extract(providerName, userAttributes);
    }

    // OAuth 서버에서 유저 정보 map으로 가져오기
    private Map<String, Object> getUserAttributes(OauthProvider provider, OauthTokenResponse tokenResponse) {
        return WebClient.create()
                .get()
                .uri(provider.getUserInfoUrl())
                .headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    private Long saveMember(UserProfile userProfile) {
        Member member = Member.builder()
                .email(userProfile.getEmail())
                .name(userProfile.getName())
                .profile(userProfile.getImageUrl())
                .build();
        return memberRepository.saveMember(member);
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email);
    }

    private Member findOrCreatemember(UserProfile userProfile) {
        Member member = findMember(userProfile.getEmail());
        if (member == null) {
            member = Member.builder()
                    .id(saveMember(userProfile))
                    .name(userProfile.getName())
                    .profile(userProfile.getImageUrl())
                    .build();
        }
        return member;
    }
}
