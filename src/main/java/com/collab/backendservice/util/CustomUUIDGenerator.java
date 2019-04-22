package com.collab.backendservice.util;

import org.apache.commons.text.RandomStringGenerator;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by karthik on 2019-03-11
 */

/**
 * Class used for generating different types of UUIDS
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
     * Use a short alphanumeric ID generator
     * @return ID
     */
    public static String generateShortUUID() {
        char [][] stringPairs = {{'a', 'z'}, {'A', 'Z'}, {'0', '9'}};
        RandomStringGenerator shortIDGenerator = new RandomStringGenerator.Builder().withinRange(stringPairs).build();
        String shortID = shortIDGenerator.generate(6);
        return shortID;
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
