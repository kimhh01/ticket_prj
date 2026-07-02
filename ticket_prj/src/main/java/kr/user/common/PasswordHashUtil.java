package kr.user.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 비밀번호를 DB 저장 및 비교용 SHA-1 해시 문자열로 변환한다.
 */
public final class PasswordHashUtil {

	private PasswordHashUtil() {
	}

	public static String sha1(String password) {
		if (password == null) {
			throw new IllegalArgumentException("비밀번호는 null일 수 없습니다.");
		}

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
			StringBuilder hash = new StringBuilder(hashBytes.length * 2);

			for (byte hashByte : hashBytes) {
				hash.append(String.format("%02x", hashByte & 0xff));
			}

			return hash.toString();
		} catch (NoSuchAlgorithmException exception) {
			throw new IllegalStateException("SHA-1 알고리즘을 사용할 수 없습니다.", exception);
		}
	}
}
