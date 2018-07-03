package test.rsa;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RSAKey {
    private final RSAPublicKey rsaPublicKey;
    private final RSAPrivateKey rsaPrivateKey;

    public RSAKey(RSAPublicKey rsaPublicKey, RSAPrivateKey rsaPrivateKey) {
        this.rsaPublicKey = rsaPublicKey;
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public RSAPublicKey getRsaPublicKey() {
        return rsaPublicKey;
    }

    public RSAPrivateKey getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    @Override
    public String toString() {
        return "RSAKey [rsaPublicKey=" + RSAUtils.encode(rsaPublicKey.getEncoded()) + ", rsaPrivateKey="
                + RSAUtils.encode(rsaPrivateKey.getEncoded()) + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rsaPrivateKey == null) ? 0 : rsaPrivateKey.hashCode());
        result = prime * result + ((rsaPublicKey == null) ? 0 : rsaPublicKey.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof RSAKey
                && ((RSAKey) obj).getRsaPublicKey().getModulus().equals(this.getRsaPublicKey().getModulus())
                && ((RSAKey) obj).getRsaPublicKey().getPublicExponent()
                        .equals(this.getRsaPublicKey().getPublicExponent())
                && ((RSAKey) obj).getRsaPrivateKey().getModulus().equals(this.getRsaPrivateKey().getModulus())
                && ((RSAKey) obj).getRsaPrivateKey().getPrivateExponent()
                        .equals(this.getRsaPrivateKey().getPrivateExponent())) {
            return true;
        }
        return false;
    }
}
