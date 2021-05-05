package io.celsoagra.finance.base;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import io.celsoagra.finance.util.CryptUtil;

public class Wallet {
    
    private PrivateKey privateKey;
    private PublicKey publicKey;
    
    public Wallet() {
        generateKeyPair();       
    }

    public String getPublicKeyAsString() {
        PublicKey key = getPublicKey();

        byte[] byte_pubkey = key.getEncoded();
        return Base64.getEncoder().encodeToString(byte_pubkey);
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
    
    public PrivateKey getPrivateKey() {
        return privateKey;
    }
    
    public String generateSignature(String reciepient, double value)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException {
        String data = CryptUtil.getStringFromKey(publicKey) + reciepient + Double.toString(value);
        return Base64.getEncoder().encodeToString(CryptUtil.applyECDSASig(privateKey, data));
    }

    public void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            // Initialize the key generator and generate a KeyPair
            keyGen.initialize(ecSpec, random); // 256 bytes provides an acceptable security level
            KeyPair keyPair = keyGen.generateKeyPair();

            // Set the public and private keys from the keyPair
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
