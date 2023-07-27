package kr.codesquad.issuetracker.infrastructure.security.hash;

public interface PasswordEncoder {

	String encrypt(final String text);
}
