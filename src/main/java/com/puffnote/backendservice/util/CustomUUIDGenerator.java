package com.puffnote.backendservice.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by karthik on 2019-03-11
 */
public class CustomUUIDGenerator {

    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public CustomUUIDGenerator() {}

    /**
     * Use Java's UUID Random Generator
     * @return UUID
     */
    public static String generateRandomUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * Use Java's UUID Random Generator combined with the SHA256 digest for uniqueness
     * @return UUID
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public String generateSHABasedUUID() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest salt = MessageDigest.getInstance("SHA-256");
        salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
        String digest = bytesToHex(salt.digest());
        return digest;
    }

    /**
     * Helper method to convert bytes to hex
     * @param bytes
     * @return Converted Hex Chars
     */
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
