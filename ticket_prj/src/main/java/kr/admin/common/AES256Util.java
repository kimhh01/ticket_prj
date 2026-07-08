package kr.admin.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256Util {

    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final String KEY_ALGORITHM = "AES";
    private static final String PREFIX = "AESGCM";

    private static final int IV_LENGTH_BYTE = 12;
    private static final int TAG_LENGTH_BIT = 128;
    private static final int AES_256_KEY_LENGTH_BYTE = 32;

    private static final String CRYPTO_KEY_PROPERTY = "admin.crypto.key";
    private static final String CRYPTO_KEY_ENV = "ADMIN_CRYPTO_KEY";
    private static final String SECRET_PATH_PROPERTY = "admin.secret.path";
    private static final String DEFAULT_SECRET_FILE_PATH = "config/admin-secret.properties";

    private AES256Util() {
    }

    public static String encrypt(String plainText) {

        if (plainText == null) {
            return null;
        }

        if (plainText.isEmpty()) {
            return "";
        }

        if (isEncrypted(plainText)) {
            return plainText;
        }

        try {
            byte[] iv = createIv();

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    getSecretKey(),
                    new GCMParameterSpec(TAG_LENGTH_BIT, iv)
            );

            byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            byte[] combined = new byte[iv.length + cipherText.length];

            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(cipherText, 0, combined, iv.length, cipherText.length);

            return PREFIX + "$" + Base64.getEncoder().encodeToString(combined);

        } catch (Exception e) {
            throw new RuntimeException("개인정보 암호화 처리 중 오류가 발생했습니다.", e);
        }
    }

    public static String decrypt(String encryptedText) {

        if (encryptedText == null) {
            return null;
        }

        if (encryptedText.isEmpty()) {
            return "";
        }

        // 기존 평문 데이터가 남아 있어도 화면이 깨지지 않도록 그대로 반환합니다.
        if (!isEncrypted(encryptedText)) {
            return encryptedText;
        }

        try {
            String base64Text = encryptedText.substring((PREFIX + "$").length());

            byte[] combined = Base64.getDecoder().decode(base64Text);

            byte[] iv = Arrays.copyOfRange(combined, 0, IV_LENGTH_BYTE);
            byte[] cipherText = Arrays.copyOfRange(combined, IV_LENGTH_BYTE, combined.length);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(
                    Cipher.DECRYPT_MODE,
                    getSecretKey(),
                    new GCMParameterSpec(TAG_LENGTH_BIT, iv)
            );

            byte[] plainText = cipher.doFinal(cipherText);

            return new String(plainText, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("개인정보 복호화 처리 중 오류가 발생했습니다.", e);
        }
    }

    public static boolean isEncrypted(String value) {
        return value != null && value.startsWith(PREFIX + "$");
    }

    public static String generateBase64Key() {

        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
            keyGenerator.init(AES_256_KEY_LENGTH_BYTE * 8);

            SecretKey secretKey = keyGenerator.generateKey();

            return "base64:" + Base64.getEncoder().encodeToString(secretKey.getEncoded());

        } catch (Exception e) {
            throw new RuntimeException("AES 키 생성 중 오류가 발생했습니다.", e);
        }
    }

    private static SecretKeySpec getSecretKey() {

        String keyText = loadCryptoKey();

        byte[] keyBytes = createKeyBytes(keyText.trim());

        return new SecretKeySpec(keyBytes, KEY_ALGORITHM);
    }

    private static String loadCryptoKey() {

        // 1. Tomcat VM arguments에서 직접 키 확인
        String keyText = System.getProperty(CRYPTO_KEY_PROPERTY);

        if (isNotBlank(keyText)) {
            return keyText.trim();
        }

        // 2. 환경변수에서 키 확인
        keyText = System.getenv(CRYPTO_KEY_ENV);

        if (isNotBlank(keyText)) {
            return keyText.trim();
        }

        // 3. Tomcat VM arguments에서 properties 파일 경로 확인
        String secretPath = System.getProperty(SECRET_PATH_PROPERTY);

        if (isNotBlank(secretPath)) {
            keyText = loadCryptoKeyFromFile(secretPath.trim());

            if (isNotBlank(keyText)) {
                return keyText.trim();
            }
        }

        // 4. 기본 경로 config/admin-secret.properties 확인
        keyText = loadCryptoKeyFromFile(DEFAULT_SECRET_FILE_PATH);

        if (isNotBlank(keyText)) {
            return keyText.trim();
        }

        throw new IllegalStateException(
                "AES 암호화 키가 설정되지 않았습니다. "
                        + "다음 중 하나를 설정하세요. "
                        + "1) Tomcat VM arguments: -Dadmin.crypto.key=키값 "
                        + "2) 환경변수: ADMIN_CRYPTO_KEY "
                        + "3) Tomcat VM arguments: -Dadmin.secret.path=키파일경로 "
                        + "4) 기본 파일: config/admin-secret.properties"
        );
    }

    private static String loadCryptoKeyFromFile(String filePath) {

        File file = new File(filePath);

        if (!file.exists() || !file.isFile()) {
            return null;
        }

        Properties properties = new Properties();

        try (InputStream inputStream = new FileInputStream(file)) {

            properties.load(inputStream);

            return properties.getProperty(CRYPTO_KEY_PROPERTY);

        } catch (IOException e) {
            throw new RuntimeException("AES 키 설정 파일을 읽는 중 오류가 발생했습니다. filePath=" + filePath, e);
        }
    }

    private static byte[] createKeyBytes(String keyText) {

        try {
            if (keyText.startsWith("base64:")) {
                byte[] decodedKey = Base64.getDecoder().decode(keyText.substring("base64:".length()));

                if (decodedKey.length != 16
                        && decodedKey.length != 24
                        && decodedKey.length != 32) {
                    throw new IllegalArgumentException("AES 키는 16, 24, 32 byte 중 하나여야 합니다.");
                }

                return decodedKey;
            }

            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

            return sha256.digest(keyText.getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            throw new RuntimeException("AES 키 처리 중 오류가 발생했습니다.", e);
        }
    }

    private static byte[] createIv() {

        byte[] iv = new byte[IV_LENGTH_BYTE];

        new SecureRandom().nextBytes(iv);

        return iv;
    }

    private static boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }
}