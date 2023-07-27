package kr.codesquad.issuetracker.infrastructure.security.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;

public class SHA256 implements PasswordEncoder {

	@Override
	public String encrypt(final String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(text.getBytes());
			return bytesToHex(md.digest());
		} catch (NoSuchAlgorithmException | NullPointerException e) {
			throw new ApplicationException(ErrorCode.FAILED_ENCRYPTION);
		}
	}

	private String bytesToHex(byte[] bytes) {
		StringBuilder hexBuilder = new StringBuilder();
		for (byte b : bytes) {
			hexBuilder.append(String.format("%02x", b));
		}
		return hexBuilder.toString();
	}
}

