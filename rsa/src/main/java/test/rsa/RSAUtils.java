package test.rsa;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSAUtils {
    private static final String KEY_ALGORITHM = "RSA";
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    private static final int KEY_SIZE = 1 << 9;
    private static KeyPairGenerator keyPairGenerator = null;
    private static KeyFactory keyFactory = null;

    public static void initKeyPairGenerator() throws NoSuchAlgorithmException {
        if (keyPairGenerator == null) {
            synchronized (RSAUtils.class) {
                if (keyPairGenerator == null) {
                    keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
                    keyPairGenerator.initialize(KEY_SIZE);
                }
            }
        }
    }

    public static void initKeyFactory() throws NoSuchAlgorithmException {
        if (keyFactory == null) {
            synchronized (RSAUtils.class) {
                if (keyFactory == null) {
                    keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
                }
            }
        }
    }

    public static byte[] RSAEncode(PublicKey key, byte[] input) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(input);
    }

    public static byte[] RSADecode(PrivateKey key, byte[] input) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(input);
    }

    public static RSAKey getRSAKey() throws GeneralSecurityException {
        initKeyPairGenerator();
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return new RSAKey((RSAPublicKey) keyPair.getPublic(), (RSAPrivateKey) keyPair.getPrivate());
    }

    public static RSAPublicKey genRSAPublicKey(byte[] encodedKey) throws GeneralSecurityException {
        initKeyFactory();
        return (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    public static String encode(byte[] bs) {
        return new String(Base64.getEncoder().encode(bs));
    }

    public static byte[] decode(String str) {
        return Base64.getDecoder().decode(str.getBytes());
    }

    public static MED getMED(RSAKey rsaKey) {
        return new MED(rsaKey.getRsaPublicKey().getModulus(), rsaKey.getRsaPublicKey().getPublicExponent(), rsaKey
                .getRsaPrivateKey().getPrivateExponent());
    }

    public static RSAKey getRSAKey(MED med) throws GeneralSecurityException {
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(med.getModulus(), med.getPublicExponent());
        RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(med.getModulus(), med.getPrivateExponent());
        return new RSAKey((RSAPublicKey) keyFactory.generatePublic(publicKeySpec),
                (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec));
    }
}
