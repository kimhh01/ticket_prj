package kr.admin.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHashUtil {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final String PREFIX = "PBKDF2";
    private static final int ITERATIONS = 600000;
    private static final int SALT_LENGTH_BYTE = 16;
    private static final int KEY_LENGTH_BIT = 256;

    private PasswordHashUtil() {
    }

    public static String hashPassword(String plainPassword) {

        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수 입력값입니다.");
        }

        try {
            byte[] salt = createSalt();
            byte[] hash = pbkdf2(plainPassword.toCharArray(), salt, ITERATIONS, KEY_LENGTH_BIT);

            return PREFIX + "$"
                    + ITERATIONS + "$"
                    + Base64.getEncoder().encodeToString(salt) + "$"
                    + Base64.getEncoder().encodeToString(hash);

        } catch (Exception e) {
            throw new RuntimeException("비밀번호 암호화 처리 중 오류가 발생했습니다.", e);
        }
    }

    public static boolean matches(String plainPassword, String storedPasswordHash) {

        if (plainPassword == null || storedPasswordHash == null) {
            return false;
        }

        if (!isPbkdf2Hash(storedPasswordHash)) {
            return false;
        }

        try {
            String[] parts = storedPasswordHash.split("\\$");

            int iterations = Integer.parseInt(parts[1]);
            byte[] salt = Base64.getDecoder().decode(parts[2]);
            byte[] storedHash = Base64.getDecoder().decode(parts[3]);

            byte[] inputHash = pbkdf2(
                    plainPassword.toCharArray(),
                    salt,
                    iterations,
                    storedHash.length * 8
            );

            return MessageDigest.isEqual(storedHash, inputHash);

        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPbkdf2Hash(String value) {

        if (value == null) {
            return false;
        }

        String[] parts = value.split("\\$");

        return parts.length == 4 && PREFIX.equals(parts[0]);
    }

    public static boolean matchesLegacySha1(String plainPassword, String storedPasswordHash) {

        if (plainPassword == null || storedPasswordHash == null) {
            return false;
        }

        if (!storedPasswordHash.matches("^[0-9A-Fa-f]{40}$")) {
            return false;
        }

        String inputSha1 = sha1Hex(plainPassword);

        return storedPasswordHash.equalsIgnoreCase(inputSha1);
    }

    private static byte[] createSalt() {
        byte[] salt = new byte[SALT_LENGTH_BYTE];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int keyLengthBit)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLengthBit);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);

        try {
            return factory.generateSecret(spec).getEncoded();
        } finally {
            spec.clearPassword();
        }
    }

    private static String sha1Hex(String plainText) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = messageDigest.digest(plainText.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();

            for (byte b : bytes) {
                sb.append(String.format("%02X", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 처리 중 오류가 발생했습니다.", e);
        }
    }
}
