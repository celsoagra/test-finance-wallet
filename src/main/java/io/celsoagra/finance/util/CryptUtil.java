package io.celsoagra.finance.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CryptUtil {

    private static final String ALG_SHA256 = "SHA-256";
    private static final String CHARSET_ENCODING_UTF8 = "UTF-8";

    public static String applySha256(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuffer hexString = new StringBuffer();
        byte[] hash = MessageDigest.getInstance(ALG_SHA256).digest(input.getBytes(CHARSET_ENCODING_UTF8));
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static byte[] applyECDSASig(PrivateKey privateKey, String input)
            throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
        Signature dsa;
        byte[] output = new byte[0];
        dsa = Signature.getInstance("ECDSA", "BC");
        dsa.initSign(privateKey);
        byte[] strByte = input.getBytes();
        dsa.update(strByte);
        byte[] realSig = dsa.sign();
        output = realSig;
        return output;
    }

    public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature)
            throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
        Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
        ecdsaVerify.initVerify(publicKey);
        ecdsaVerify.update(data.getBytes());
        return ecdsaVerify.verify(signature);
    }

    public static String getStringFromKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static PublicKey getKeyFromString(String keyStr) {
        PublicKey key = null;
        try {
            X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(Base64.getDecoder().decode(keyStr));
            KeyFactory kf = KeyFactory.getInstance("ECDSA", "BC");
            key = kf.generatePublic(X509publicKey);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchProviderException e) {
            e.printStackTrace();
        }

        return key;
    }
}
