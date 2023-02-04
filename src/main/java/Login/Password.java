package Login;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

public class Password {
    String plainPassword;
    int minPasswordLength = 8;

    public String getPassword() {
        return plainPassword;
    }

    public String setPassword(String plainPassword) {
        return plainPassword;
    }

    public Password(String password) {
        this.plainPassword = password;

    }

    public int passwordStrength(String password) {
        int passwordStrength = 0;
        if (password.matches(".*\\W.*")) {
            passwordStrength += 1;
        }
        if (password.matches(".*[a-z].*")) {
            passwordStrength += 1;
        }
        if (password.matches(".*[A-Z].*")) {
            passwordStrength += 1;
        }
        if (password.matches(".*[0-9].*")) {
            passwordStrength += 1;
        }
        return passwordStrength;
    }

    private String encryptPassword() throws Exception {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2048);

        KeyPair keyPair = keyGenerator.genKeyPair();

        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        // Szyfrowanie
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedMessageBytes = encryptCipher.doFinal(plainPassword.getBytes());
        String encryptedPassword = Base64.getEncoder().encodeToString(encryptedMessageBytes);
        System.out.println("Encrypted Password: " + encryptedPassword);

        // Odszyfrowanie
        encryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = encryptCipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        String decryptedText = new String(decryptedBytes);
        System.out.println(decryptedText);

        return encryptedPassword;
    }
}
