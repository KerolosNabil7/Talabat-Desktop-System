package talabat;

import java.security.*;

public class PasswordHashing {

    private final char[] hexdecimalArray = "0123456789ABCDEF".toCharArray();

    public String GenerateHash(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        byte[] hash = digest.digest(password.getBytes());
        return bytestoString(hash);
    }

    public String bytestoString(byte[] bytes) {
        char[] hexaChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int x = bytes[j] & 0xFF;
            hexaChars[j * 2] = hexdecimalArray[x >>> 4];
            hexaChars[j * 2 + 1] = hexdecimalArray[x & 0x0F];
        }
        return new String(hexaChars);
    }
}