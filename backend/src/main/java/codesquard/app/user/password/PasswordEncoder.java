package codesquard.app.user.password;

import static java.nio.charset.StandardCharsets.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Component;

import codesquard.app.api.errors.errorcode.PasswordErrorCode;
import codesquard.app.api.errors.exception.RestApiException;

@Component
public class PasswordEncoder {
	public String encrypt(String password) {
		try {
			SecureRandom random = new SecureRandom(); // 매번 다른 솔트 값을 생성
			byte[] salt = new byte[16];
			random.nextBytes(salt); // salt에 랜덤한 byte를 생성하여 저장

			// 비밀번호와 솔트값을 기반으로 암호화에 필요한 스펙을 생성합니다.
			PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), getSalt(password), 85319, 128);
			// 클래스를 사용하여 PBKDF2 알고리즘과 HMAC-SHA1 해시 함수를 사용하여 비밀번호를 암호화하는 SecretKey 객체를 생성합니다.
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			// 해시값을 바이트 형태로 생성
			byte[] hash = factory.generateSecret(spec).getEncoded();
			// 바이트 해시 값을 가지고 문자열로 암호화
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RestApiException(PasswordErrorCode.FAIL_PASSWORD_ENCRYPT);
		}
	}

	private byte[] getSalt(String password) throws NoSuchAlgorithmException {
		// MessageDigest 클래스를 사용하여 해시 함수 초기화, SHA-512 함수를 사용합니다.
		// 주어진 비밀번호를 해시하여 솔트 값을 생성하는데 사용됩니다.
		MessageDigest digest = MessageDigest.getInstance("SHA-512");
		byte[] keyBytes = password.getBytes(UTF_8);
		// 비밀번호 바이트 배열을 "digest" 객체의 "digest" 메소드에 전달하여 해시 값을 계산합니다.
		// 이 값은 솔트값이 될것입니다.
		return digest.digest(keyBytes);
	}
}
