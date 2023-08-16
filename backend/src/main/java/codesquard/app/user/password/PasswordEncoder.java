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

			PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), getSalt(password), 85319, 128);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			// 해시값 생성
			byte[] hash = factory.generateSecret(spec).getEncoded();
			// 해시값을 가지고 암호화
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RestApiException(PasswordErrorCode.FAIL_PASSWORD_ENCRYPT);
		}
	}

	private byte[] getSalt(String password) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-512");
		byte[] keyBytes = password.getBytes(UTF_8);
		return digest.digest(keyBytes);
	}
}
