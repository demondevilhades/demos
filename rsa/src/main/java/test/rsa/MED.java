package test.rsa;

import java.io.Serializable;
import java.math.BigInteger;

@SuppressWarnings("serial")
public class MED implements Serializable {
    private final BigInteger modulus;
    private final BigInteger publicExponent;
    private final BigInteger privateExponent;

    public MED(BigInteger modulus, BigInteger publicExponent, BigInteger privateExponent) {
        this.modulus = modulus;
        this.publicExponent = publicExponent;
        this.privateExponent = privateExponent;
    }

    public BigInteger getModulus() {
        return modulus;
    }

    public BigInteger getPublicExponent() {
        return publicExponent;
    }

    public BigInteger getPrivateExponent() {
        return privateExponent;
    }

    @Override
    public String toString() {
        return "MED [modulus=" + modulus + ", publicExponent=" + publicExponent + ", privateExponent="
                + privateExponent + "]";
    }
}
