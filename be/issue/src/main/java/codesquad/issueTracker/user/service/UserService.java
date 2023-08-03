package codesquad.issueTracker.user.service;

import codesquad.issueTracker.jwt.domain.OauthAttributes;
import codesquad.issueTracker.jwt.domain.UserProfile;
import codesquad.issueTracker.jwt.dto.OauthTokenResponse;
import codesquad.issueTracker.jwt.util.InMemoryProviderRepository;
import codesquad.issueTracker.jwt.util.OauthProvider;
import codesquad.issueTracker.user.domain.LoginType;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;
import codesquad.issueTracker.jwt.domain.Jwt;
import codesquad.issueTracker.jwt.domain.Token;
import codesquad.issueTracker.jwt.dto.RequestRefreshTokenDto;
import codesquad.issueTracker.jwt.util.JwtProvider;
import codesquad.issueTracker.user.domain.User;
import codesquad.issueTracker.user.dto.LoginRequestDto;
import codesquad.issueTracker.user.dto.LoginResponseDto;
import codesquad.issueTracker.user.dto.SignUpRequestDto;
import codesquad.issueTracker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;
	private final InMemoryProviderRepository inMemoryProviderRepository;
	private final String DEFAULT_PROFILE_IMG = "https://upload.wikimedia.org/wikipedia/commons/1/17/Enhydra_lutris_face.jpg";

	public Long save(SignUpRequestDto userSignUpRequestDto) {
		validateDuplicatedEmail(userSignUpRequestDto);
		String encodedPassword = BCrypt.hashpw(userSignUpRequestDto.getPassword(), BCrypt.gensalt());
		User user = SignUpRequestDto.toEntity(userSignUpRequestDto, encodedPassword, DEFAULT_PROFILE_IMG);
		return userRepository.insert(user);
	}

	/**
	 * 1. 이미 등록된 유저인지 확인
	 * 2. 로그인 성공 여부 체크
	 * 3. access token, refresh token 발급
	 * 4. 처음 로그인 하는 유저라면 refresh token db insert
	 * 5. 재로그인하는 유저라면 refresh token db update
	 * 5. user 정보와 access token, refresh token 응답
	 */
	public LoginResponseDto login(LoginRequestDto loginRequestDto) {
		User user = userRepository.findByEmail(loginRequestDto.getEmail())
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
		if(user.getPassword() == null){
			throw new CustomException(ErrorCode.GITHUB_LOGIN_USER);
		}
		validateLoginUser(loginRequestDto, user);
		Token token = userRepository.findTokenByUserId(user.getId()).orElse(null);
		Jwt jwt = jwtProvider.createJwt(Map.of("userId", user.getId()));
		if (token == null) {
			userRepository.insertRefreshToken(user.getId(), jwt.getRefreshToken());
		} else {
			userRepository.updateRefreshToken(user.getId(), jwt.getRefreshToken());
		}
		return LoginResponseDto.builder()
				.userId(user.getId())
				.profileImgUrl(user.getProfileImg())
				.accessToken(jwt.getAccessToken())
				.refreshToken(jwt.getRefreshToken())
				.build();
	}

	public void validateLoginUser(LoginRequestDto loginRequestDto, User user) {
		if (!loginRequestDto.getEmail().equals(user.getEmail())
				|| !BCrypt.checkpw(loginRequestDto.getPassword(), user.getPassword()))
			throw new CustomException(ErrorCode.FAILED_LOGIN_USER);
	}

	public void validateDuplicatedEmail(SignUpRequestDto signUpRequestDto) {
		if (!userRepository.findByEmail(signUpRequestDto.getEmail()).isEmpty()) {
			throw new CustomException(ErrorCode.ALREADY_EXIST_USER);
		}
	}

	/**
	 * 1. 만료된 리프레시 토큰이면 예외처리
	 * 2. DB에 없는 리프레시 토큰이면 예외처리
	 */
	public String reissueAccessToken(RequestRefreshTokenDto refreshTokenDto) {
		jwtProvider.getClaims(refreshTokenDto.getRefreshToken());

		Token token = userRepository.findTokenByUserToken(refreshTokenDto.getRefreshToken())
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REFRESH_TOKEN));
		return jwtProvider.reissueAccessToken(Map.of("userId", token.getUserId()));
	}

	public void logout(HttpServletRequest request) {
		Long userId = Long.parseLong(String.valueOf(request.getAttribute("userId")));
		userRepository.deleteTokenByUserId(userId)
				.orElseThrow(() -> new CustomException(ErrorCode.DELETE_FAIL));
	}

	public LoginResponseDto oauthLogin(String providerName, String code) {
		OauthProvider provider = inMemoryProviderRepository.findByProviderName(providerName);

		OauthTokenResponse tokenResponse = getToken(code, provider);

		User user = getUserProfile(providerName, tokenResponse, provider).toUser(providerName);
		User existUser = userRepository.findByEmail(user.getEmail()).orElse(null);
		Long userId;
		if(existUser != null){
			if(!existUser.getLoginType().getType().equals(providerName) && existUser.getLoginType() != LoginType.LOCAL) {
				throw new CustomException(ErrorCode.ALREADY_EXIST_USER);
			} else if (existUser.getLoginType().getType().equals(providerName)) {
				userId = existUser.getId();
//				userRepository.deleteTokenByUserId(userId);
			} else {
				userId = userRepository.updateUserLoginType(existUser, user);
//				userRepository.deleteTokenByUserId(userId);
			}
		} else {
			userId = userRepository.insert(user);
		}

		Jwt jwt = jwtProvider.createJwt(Map.of("userId", userId));
		userRepository.insertRefreshToken(userId, jwt.getRefreshToken());

		return LoginResponseDto.builder()
				.userId(userId)
				.accessToken(jwt.getAccessToken())
				.refreshToken(jwt.getRefreshToken())
				.profileImgUrl(user.getProfileImg())
				.build();
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
				.bodyValue(tokenRequest(code,provider))
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

	private Map<String, Object> getUserAttributes(OauthProvider provider, OauthTokenResponse tokenResponse) {
		return WebClient.create()
				.get()
				.uri(provider.getUserInfoUrl())
				.headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
				.block();
	}
}
