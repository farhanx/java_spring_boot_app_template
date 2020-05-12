/*
 * PKI FRAPPER
 * @description Operations for public and private keys
 * @author Farhan Hameed Khan <farhankhan@blockchainsfalcon.com> | <farhanhbk@hotmail.com>
 * @date 1-May-2019
 * @version 1.1.0
 * @link http://www.blockchainsfalcon.com
 */
package com.springboot.template.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class CryptoUtilities {

    public final static String ADMIN_ROLE ="ROLE_ADMIN";
    public final static String USER_ROLE ="ROLE_USER";


    /**+
     *
     * @param strTextContent Here you can give text file content or text which you want to hash
     * @param algo  Here you can tell which algorithim you need , you can use SHA-256 as a param
     * @return
     */
    public static String convertToHash(String strTextContent,String algo) //"SHA-256"
    {
        String hashtext ="";
        try {
            MessageDigest digest = MessageDigest.getInstance(algo);
            byte[] b = digest.digest(strTextContent.getBytes(StandardCharsets.UTF_8));

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, b);

            // Convert message digest into hex value
            hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();

        }

        return hashtext;
    }

    /**+
     *
     * @param strTextContent Here you can give text file content or text which you want to hash
     * @param algo  Here you can tell which algorithim you need , you can use SHA-256 as a param
     * @return
     */
    public static String convertBytesToHash(byte textContent[],String algo) //"SHA-256"
    {
        String hashtext ="";
        try {
            MessageDigest digest = MessageDigest.getInstance(algo);
            byte[] b = digest.digest(textContent);

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, b);

            // Convert message digest into hex value
            hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();

        }

        return hashtext;
    }

    static public String convertBytesToString(byte bytesdata[])
    {
        String ret = new String(bytesdata);
        return ret;
    }



    public static String hexToASCII(String hexValue)
    {
        StringBuilder output = new StringBuilder("");
        for (int i = 2; i < hexValue.length(); i += 2)
        {
            String str = hexValue.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }

    public static String asciiToHex(String asciiValue)
    {
        char[] chars = asciiValue.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++)
        {
            hex.append(Integer.toHexString((int) chars[i]));
        }
        return hex.toString() + "".join("", Collections.nCopies(32 - (hex.length()/2), "00"));
    }


    public static byte[] stringToBytes32(String string) {
        byte[] byteValue = string.getBytes();
        byte[] byteValueLen32 = new byte[32];
        System.arraycopy(byteValue, 0, byteValueLen32, 0, byteValue.length);
        return byteValueLen32;
    }

    public static Long getUnixTimeStamp()
    {
        return (System.currentTimeMillis() / 1000L);
    }

    public static Long getUnixTimeStampWithAddedDays(long days)
    {
        long currenTime = System.currentTimeMillis();
        long newAddedDays = currenTime + TimeUnit.DAYS.toMillis(days);
        return (newAddedDays / 1000L);
    }

    public static Long getUnixTimeStampWithAddedMinutes(long mins)
    {
        long currenTime = System.currentTimeMillis();
        long newAddedMins = currenTime + TimeUnit.MINUTES.toMillis(mins);
        return (newAddedMins / 1000L);
    }


    public static String getCurrentUserLogin() {
        org.springframework.security.core.context.SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String login = null;
        if (authentication != null)
            if (authentication.getPrincipal() instanceof UserDetails)
                login = ((UserDetails) authentication.getPrincipal()).getUsername();
            else if (authentication.getPrincipal() instanceof String)
                login = (String) authentication.getPrincipal();

        return login;
    }

    static public String[] generateKeyPair() {

        String[] ret=new String[2];

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024, new SecureRandom());
            KeyPair keyPair= keyPairGenerator.generateKeyPair();

            byte[] publicKey = keyPair.getPublic().getEncoded();
            byte[] privateKey = keyPair.getPrivate().getEncoded();

            ret[0] =  Base64.getEncoder().encodeToString(publicKey);
            ret[1] =  Base64.getEncoder().encodeToString(privateKey);

            System.out.println("Private Key"+ret[1]);
        } catch (GeneralSecurityException e) {
            throw new AssertionError(e);
        }

        return ret;
    }

    static public byte[] textSigning(PrivateKey privKey,String txtToSign)
    {
        byte ret[]= {0};

        try {

            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(privKey);
            sign.update(txtToSign.getBytes());
            byte signedDatafinal[] = sign.sign();

            return signedDatafinal;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }


        return ret;
    }

    static public boolean isValidSignatureMatches(PublicKey publicKey,byte[] signedData, byte[] unsignedData)
    {
        boolean bret = false;

        try {

            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initVerify(publicKey);
            sign.update(unsignedData);

            bret = sign.verify(signedData);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }


        return bret;

    }


    static public PrivateKey getPrivateKeyFromString(String privKeyStr)
    {
        byte[] sigBytes = new byte[0];

        PrivateKey privateKey = null;

        try {
            sigBytes = Base64.getDecoder().decode(privKeyStr.getBytes("UTF-8"));

            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(sigBytes);
            KeyFactory keyFact = null;

            keyFact = KeyFactory.getInstance("RSA");


            privateKey = keyFact.generatePrivate(privateKeySpec);  //throws exception
        }
        catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return privateKey;
    }

    static public PublicKey getPublicKeyFromString(String publicKeyStr)
    {
        byte[] sigBytes = new byte[0];

        PublicKey publicKey = null;

        try {
            sigBytes = Base64.getDecoder().decode(publicKeyStr.getBytes("UTF-8"));
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(sigBytes);
            KeyFactory keyFact = null;
            keyFact = KeyFactory.getInstance("RSA");

            publicKey = keyFact.generatePublic(publicKeySpec);  //throws exception
        }
        catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return publicKey;
    }


    // Method to encode a string value using `UTF-8` encoding scheme
    public static String encodeValueForURL(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    // Method to encode a string value using `UTF-8` encoding scheme
    public static String decodeValueForURL(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

}
