/** Author: Brian Erichsen Fagundes
 * Computer Networking and Security CS6014
 * TLS Homework
 * Spring 2024 - UofU MSD */

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;

public class Share {


    //Diffie-Hellman
    // https://www.ietf.org/rfc/rfc3526.txt
    /****
     * 1536-bit MODP Group
     *
     *    The 1536 bit MODP group has been used for the implementations for
     *    quite a long time, but was not defined in RFC 2409 (IKE).
     *    Implementations have been using group 5 to designate this group, we
     *    standardize that practice here.
     *
     *    The prime is: 2^1536 - 2^1472 - 1 + 2^64 * { [2^1406 pi] + 741804 }
     *
     *    Its hexadecimal value is:
     *
     *       FFFFFFFF FFFFFFFF C90FDAA2 2168C234 C4C6628B 80DC1CD1
     *       29024E08 8A67CC74 020BBEA6 3B139B22 514A0879 8E3404DD
     *       EF9519B3 CD3A431B 302B0A6D F25F1437 4FE1356D 6D51C245
     *       E485B576 625E7EC6 F44C42E9 A637ED6B 0BFF5CB6 F406B7ED
     *       EE386BFB 5A899FA5 AE9F2411 7C4B1FE6 49286651 ECE45B3D
     *       C2007CB8 A163BF05 98DA4836 1C55D39A 69163FA8 FD24CF5F
     *       83655D23 DCA3AD96 1C62F356 208552BB 9ED52907 7096966D
     *       670C354E 4ABC9804 F1746C08 CA237327 FFFFFFFF FFFFFFFF
     *
     *    The generator is: 2.
     *
     * */
    static private final String safePrime_1536 = "FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA237327FFFFFFFFFFFFFFFF";
    static public BigInteger n = new BigInteger(safePrime_1536, 16);
    static public BigInteger g = new BigInteger("2");

    static Certificate _CACertificate = Share.getCertificate("/Users/brianerichsenfagundes/Desktop/TSL/CAcertificate.pem");

    //The 6 keys
    static byte[] clientEncrypt;
    static byte[] serverEncrypt;
    static byte[] clientMAC;
    static byte[] serverMAC;
    static byte[] clientIVector;
    static byte[] serverIVector;


    //This methiod generates a public key from Certificate
    static public Certificate getCertificate(String filepath) {
        try {
            //Get public key from passed in file path
            FileInputStream fileInput = new FileInputStream(filepath);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            //Certificate
            return certificateFactory.generateCertificate(fileInput);

        }catch (FileNotFoundException | CertificateException e){
            throw new RuntimeException(e);
        }
    }

    //Generate DH-public key from DH shared params and DH-private key
    public static BigInteger getDiffiePublicKey(BigInteger _DHprivateKey) {
        return g.modPow(_DHprivateKey, n);
    }

    //Generate signed Diffie-Hellman public key from input
    static public BigInteger getSignedDHPublicKey(String privateKeyDer_file,
                                                  BigInteger _DHpublicKey, PublicKey _RSAPublicKey){
        try {
            //Read in privateKeyDer file, extract the private keys bytes and encode it
            FileInputStream privateKeyDer_file_input = new FileInputStream(privateKeyDer_file);
            byte[] privateKeyBytes = privateKeyDer_file_input.readAllBytes();
            PKCS8EncodedKeySpec encodedPrivateKeyBytes = new PKCS8EncodedKeySpec(privateKeyBytes);

            //Generates private RSA key
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateRSAKey =  keyFactory.generatePrivate(encodedPrivateKeyBytes);

            //Sign with private key
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateRSAKey);
            signature.update(_DHpublicKey.toByteArray());
            byte[] signedPublicKey = signature.sign();

            signature.initVerify(_RSAPublicKey);
            signature.update(_DHpublicKey.toByteArray());

            return new BigInteger(signedPublicKey);

        }catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException |
                SignatureException e){
            throw new RuntimeException(e);
        }
    }
    //Verifies certificate and signs based on public key

    //Generate shared secret
    public static BigInteger getSharedSecret(BigInteger _publicDHKey, BigInteger _privateDHKey) {
        return _publicDHKey.modPow(_privateDHKey, n);
    }
    public static void verifyCertificate(Certificate certificate) throws CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException {
        PublicKey _CApublicKey = Share._CACertificate.getPublicKey();
        certificate.verify(_CApublicKey);
    }

    //Key derivation function (KDF) hdkf. Uses the HmacSHA256 Hashing MAC (HMAC) function.
    private static byte[] hdkfExpand(byte[] masterKey, String tag) {
        try {
            //Create mac with master key
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(masterKey, "HmacSHA256");
            hmac.init(secretKeySpec);

            //tag concatenated with a byte with value 1
            hmac.update(tag.getBytes());
            hmac.update((byte) 0x01);

            //computes the hmac and returns first 16 bytes
            return Arrays.copyOf(hmac.doFinal(), 16);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    //Generate session keys
    public static void makeSecretKeys(BigInteger clientNonce,
                                      BigInteger sharedSecretFromDiffieHellman) {
        try {
            //Create mac with nonce
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(clientNonce.toByteArray(), "HmacSHA256");
            hmac.init(secretKeySpec);

            //Add shared secret and computer mac
            hmac.update(sharedSecretFromDiffieHellman.toByteArray());
            byte[] prk = hmac.doFinal();

            //Use hdkf key derivation function to create the session keys
            serverEncrypt = hdkfExpand(prk, "server encrypt");
            clientEncrypt = hdkfExpand(serverEncrypt, "client encrypt");
            serverMAC = hdkfExpand(clientEncrypt, "server MAC");
            clientMAC = hdkfExpand(serverMAC, "client MAC");
            serverIVector = hdkfExpand(clientMAC, "server IV");
            clientIVector = hdkfExpand(serverIVector, "client IV");
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
    //Calculates a Message Authentication Code (MAC)
    public static byte[] macMessage(byte[] message, byte[] macKey) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        Mac HMAC = Mac.getInstance("HmacSHA256");

        //Generate a new key from macKey and initializes the Mac instance with the new key
        SecretKeySpec secretKeySpec = new SecretKeySpec(macKey, "HmacSHA256");
        HMAC.init(secretKeySpec);

        //initializes HMAC with messageHisotry and computes the MAC
        HMAC.update(message);
        return HMAC.doFinal();

    }
    //Creates cipher for proper data manipulation / encryption / decryption
    public static Cipher createCipher( Boolean isEncryptedCipher, byte[] encryptKey, byte[] initializationVector) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        //Create an AES/CBC/PKCS5Padding cipher
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        //Initialize a SecretKeySpec with encryptKey for AES encryption
        SecretKeySpec secretKeySpec = new SecretKeySpec(encryptKey, "AES");

        //Initialize an IvParameterSpec with the initializationVector key
        IvParameterSpec ivParam = new IvParameterSpec(initializationVector);

        //Check if this cipher is for encryption or decryption
        if(isEncryptedCipher){
            //Initialize the cipher in ENCRYPT_MODE with the SecretKeySpec and IvParameterSpec
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParam);
        }
        else{
            //Initialize the cipher in DECRYPT_MODE with the SecretKeySpec and IvParameterSpec
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParam);
        }
        return cipher;
    }

    public static byte[] encrypts(byte[] message, byte[] encryptKey, byte[] initializationVector, byte[] macKey) throws IOException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        //Initialize message to byte array output stream
        ByteArrayOutputStream encryptedMessage = new ByteArrayOutputStream();
        encryptedMessage.write(message);

        //Generate a MAC for the message using macKey and append this MAC to the end of msg_byteArrayOutputStream
        byte[] macMsg = macMessage(message, macKey);
        encryptedMessage.write(macMsg);

        //Create a AES/CBC/PKCS5Padding cipher
        Cipher cipher = createCipher(true, encryptKey, initializationVector);

        //Using the cipher, encrypt the data in encryptedMessage
        return cipher.doFinal(encryptedMessage.toByteArray());

    }
    public static String decrypts(byte[] cipherText, byte[] encryptKey, byte[] initializationVector, byte[] macKey) throws InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //Create an AES/CBC/PKCS5Padding cipher for decryption with encryptKey and initializationVector
        Cipher cipher = createCipher(false, encryptKey, initializationVector);

        //Decrypt the cipherText using the decryption cipher to get plainText
        byte[] plainText = cipher.doFinal(cipherText);

        //Separate the decrypted data into the original message (w.o MAC)
        byte[] decryptedMsg = Arrays.copyOf(plainText, plainText.length - 32);

        return new String(decryptedMsg, StandardCharsets.UTF_8);

    }

    // Helper method that compares message sender's mac with receivers mac, they should be the same
    public static void checkForValidMAC(byte[] sendersMacMsg, byte[] myMessageHistory, byte[] macKey) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        byte[] myMacMsg = Share.macMessage(myMessageHistory, macKey);
        if (!Arrays.equals(myMacMsg, sendersMacMsg)){
            throw new RuntimeException("Message MACS mismatch");
        }
    }
}//end of class outer curly bracket

