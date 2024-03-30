/** Author: Brian Erichsen Fagundes
 * Computer Networking and Security CS6014
 * TLS Homework
 * Spring 2024 - UofU MSD */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Server {
    static BigInteger serverDiffiePrivateKey;
    static BigInteger secret;

    public static void main(String[] args) throws IOException, ClassNotFoundException, CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

        //Starts the new connection
        ServerSocket ss = new ServerSocket(5864);
        System.out.println("Server started and waiting for connections...");
        Socket socket = ss.accept();
        ObjectInputStream server = new ObjectInputStream(socket.getInputStream());

        //Message history
        ByteArrayOutputStream history = new ByteArrayOutputStream();

        //Gets the first message
        BigInteger nonce = (BigInteger) server.readObject();
        history.write(nonce.toByteArray());
        System.out.println("[SERVER] reading handshake message[1]");

        //Send server certificate, server-DH-public-key and signed server-DH-public-key
        ObjectOutputStream serverOut = new ObjectOutputStream(socket.getOutputStream());

        //Generates server's Diffie-Hellman private key & serverDiffie public keys and server certificate
        serverDiffiePrivateKey = new BigInteger(new SecureRandom().generateSeed(32));
        BigInteger serverDHPublicKey = Share.getDiffiePublicKey(serverDiffiePrivateKey);
        Certificate serverCertificate = Share.getCertificate("/Users/brianerichsenfagundes/Desktop/TSL/CASignedServerCertificate.pem");

        //Creates the signed keys
        PublicKey serverRSAPublicKey = serverCertificate.getPublicKey();
        BigInteger signedServerDiffiePublicKey = Share.getSignedDHPublicKey("/Users/brianerichsenfagundes/Desktop/TSL/serverPrivateKey.der", serverDHPublicKey, serverRSAPublicKey);

        //Sends out hand-shake message
        System.out.println("[SERVER] sending handshake message[2]");
        serverOut.writeObject(serverCertificate);
        serverOut.writeObject(serverDHPublicKey);
        serverOut.writeObject(signedServerDiffiePublicKey);

        //Updates history log
        history.write(serverCertificate.toString().getBytes());
        history.write(serverDHPublicKey.toByteArray());
        history.write(signedServerDiffiePublicKey.toByteArray());

        System.out.println("[SERVER] reading handshake message[3]");
        //creates certificatate based on server incoming objects and creates diffie public key and signs it
        Certificate clientCertificate = (Certificate) server.readObject();
        BigInteger clientDiffiePublicKey = (BigInteger) server.readObject();
        BigInteger signedClientDiffiePublicKey = (BigInteger) server.readObject();

        // Verifies if certificate is same
        Share.verifyCertificate(clientCertificate);

        //Updates the history log
        history.write(clientCertificate.toString().getBytes());
        history.write(clientDiffiePublicKey.toByteArray());
        history.write(signedClientDiffiePublicKey.toByteArray());

        //Creates a shared secret
        secret = Share.getSharedSecret(clientDiffiePublicKey, serverDiffiePrivateKey);
        System.out.println("[SERVER] shared secret: " + secret);

        //Make session keys using shared secret
        Share.makeSecretKeys(nonce, secret);

        byte[] serverHandshakeFinishMsg = Share.macMessage(history.toByteArray(), Share.serverMAC);
        serverOut.writeObject(serverHandshakeFinishMsg);
        history.write(serverHandshakeFinishMsg);
        System.out.println("[SERVER] sending handshake message[4]");

       //Receive 'client Handshake Finish Msg'

        byte[] clientHSFinishMsg = (byte[]) server.readObject();
        System.out.println("[SERVER] reading handshake message[5]");

        //Compare message client's mac with our mac, they should be the same
        Share.checkForValidMAC(clientHSFinishMsg, history.toByteArray(), Share.clientMAC);
        history.write(clientHSFinishMsg);

        //Proceed to post-handshake messaging
        System.out.println("[SERVER]: Handshake was successful. Proceeding to post handshake messaging!!");

        //Encrypt and send message the first message
        String serverMsg = new String("Hello from the server!");
        byte[] encryptedServerMsg = Share.encrypts(serverMsg.getBytes(), Share.serverEncrypt, Share.serverIVector, Share.serverMAC);
        serverOut.writeObject(encryptedServerMsg);
        System.out.println("[SERVER] Msg 1| Outgoing message before encryption: " + serverMsg);
        System.out.println("[SERVER] Msg 1| Outgoing message after encryption: " + encryptedServerMsg);

        //Receives the ack message from server
        byte[] acknowledgementFromClient = (byte[]) server.readObject();

        //decrypts the message
        String decryptedAcknowledgementFromClient = Share.decrypts(acknowledgementFromClient, Share.clientEncrypt, Share.clientIVector, Share.clientMAC);
        System.out.println("[SERVER] Msg 1| Acknowledgement before decryption: " + acknowledgementFromClient);
        System.out.println("[SERVER] Msg 1| Acknowledgement after decryption: " + decryptedAcknowledgementFromClient);

        //checks that the message sent to the server is the clients "ACK"
        if (!decryptedAcknowledgementFromClient.equals("ACK")){
            throw new RuntimeException("ACK not received.");
        }
        //Read in and decrypt message from server
        byte[] clientMSG = (byte[]) server.readObject();
        String decrptedMsgFromClient = Share.decrypts(clientMSG, Share.clientEncrypt, Share.clientIVector, Share.clientMAC);
        System.out.println("[SERVER] Msg 2| Incoming message before decryption: " + clientMSG);
        System.out.println("[SERVER] Msg 2| Incoming message after decryption: " + decrptedMsgFromClient);

        //Send Acknowledgement message
        String ack = "ACK";
        byte[] encryptedACK = Share.encrypts(ack.getBytes(), Share.serverEncrypt, Share.serverIVector, Share.serverMAC);
        serverOut.writeObject(encryptedACK);
        System.out.println("[SERVER] Msg 2| Acknowledgement message before encryption: " + ack);
        System.out.println("[SERVER] Msg 2| Acknowledgement message after encryption: " + encryptedACK);
    }//end of main bracket
}//end of class bracket
