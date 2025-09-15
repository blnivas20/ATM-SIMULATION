import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class SecurityUtil {
    public static String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPin(String pin, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hashed = md.digest(pin.getBytes());
            return Base64.getEncoder().encodeToString(hashed);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing PIN", e);
        }
    }

    public static boolean verifyPin(String enteredPin, String salt, String storedHash) {
        return hashPin(enteredPin, salt).equals(storedHash);
    }
}
