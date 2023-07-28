package kr.codesquad.issuetracker.fixture;

import kr.codesquad.issuetracker.presentation.request.LoginRequest;
import kr.codesquad.issuetracker.presentation.request.SignupRequest;

public class FixtureFactory {

	public static SignupRequest createSignupRequest(String id, String pw) {
		return new SignupRequest(id, pw);
	}

	public static LoginRequest createLoginRequest(String id, String pw) {
		return new LoginRequest(id, pw);
	}
}
