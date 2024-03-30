/** Author: Brian Erichsen Fagundes
 * Computer Networking and Security CS6014
 * TLS Homework
 * Spring 2024 - UofU MSD */

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class Client {
    static BigInteger clientDiffiePrivateKey;
    static BigInteger secret;

    public static void main(String[] args) throws IOException, ClassNotFoundException, CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchProviderException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        //The client generates a nonce and a Diffie-Hellman private key, then sends the nonce to the server
        BigInteger nonce = new BigInteger(new SecureRandom().generateSeed(32)); //tls content is 32 bytes

        //Message history
        ByteArrayOutputStream history = new ByteArrayOutputStream();

        //Establish a socket connection
        Socket clientSocket = new Socket("localhost", 5864);
        ObjectOutputStream clientOut = new ObjectOutputStream(clientSocket.getOutputStream());

        //sending message 1 of the handshake client -> server
        clientOut.writeObject(nonce);
        history.write(nonce.toByteArray());
        System.out.println("[CLIENT] sending handshake message[1]");

        //Reads the input from object
        ObjectInputStream clientIn = new ObjectInputStream(clientSocket.getInputStream());
        System.out.println("[CLIENT] reading handshake message[2]");
        Certificate serverCertificate = (Certificate) clientIn.readObject();
        BigInteger serverDHPublicKey = (BigInteger) clientIn.readObject();
        BigInteger signedServerDHPublicKey = (BigInteger) clientIn.readObject();


        //Verify
        Share.verifyCertificate(serverCertificate);

        //updates the log
        history.write(serverCertificate.toString().getBytes());
        history.write(serverDHPublicKey.toByteArray());
        history.write(signedServerDHPublicKey.toByteArray());

        //Generates shared secret
        clientDiffiePrivateKey = new BigInteger(new SecureRandom().generateSeed(32));
        secret = Share.getSharedSecret(serverDHPublicKey, clientDiffiePrivateKey);
        System.out.println("[CLIENT] shared secret: " + secret);

        //Generates clients Diffie-Hellman public key
        BigInteger clientDHPublicKey = Share.getDiffiePublicKey(clientDiffiePrivateKey);

        //Generate signed clientDHPublicKey
        Certificate clientCertificate = Share.getCertificate("/Users/brianerichsenfagundes/Desktop/TSL/CASignedClientCertificate.pem");
        PublicKey clientRSAPublicKey = clientCertificate.getPublicKey();
        BigInteger signed_clientDHPublicKey = Share.getSignedDHPublicKey("/Users/brianerichsenfagundes/Desktop/TSL/clientPrivateKey.der", clientDHPublicKey, clientRSAPublicKey);

        //Send handshake message[3]
        clientOut.writeObject(clientCertificate);
        clientOut.writeObject(clientDHPublicKey);
        clientOut.writeObject(signed_clientDHPublicKey);
        System.out.println("[CLIENT] sending handshake message[3]");

        //Add sent messages to history
        history.write(clientCertificate.toString().getBytes());
        history.write(clientDHPublicKey.toByteArray());
        history.write(signed_clientDHPublicKey.toByteArray());

        //Creates session keys using shared secret
        Share.makeSecretKeys(nonce, secret);

        byte[] serverHandshakeFinishMsg = (byte[]) clientIn.readObject();
        Share.checkForValidMAC(serverHandshakeFinishMsg, history.toByteArray(), Share.serverMAC);

        //If the server's Mac message is valid, add it to history, then send 'client Handshake Finish Msg'
        history.write(serverHandshakeFinishMsg);
        System.out.println("[CLIENT] reading handshake message[5]");

        byte[] clientHandshakeFinishMsg = Share.macMessage(history.toByteArray(), Share.clientMAC);
        clientOut.writeObject(clientHandshakeFinishMsg);
        history.write(clientHandshakeFinishMsg);
        System.out.println("[CLIENT] sending handshake message[5]");

        System.out.println("[CLIENT] Handshake was successful. Proceeding to post handshake messaging!!");

        //------------------------------post handshake messaging

        //Read in and decrypt message from server
        byte[] msgFromServer = (byte[]) clientIn.readObject();
        String decrptedMsgFromServer = Share.decrypts(msgFromServer, Share.serverEncrypt, Share.serverIVector, Share.serverMAC);
        System.out.println("[CLIENT] Msg 1| Incoming message before decryption: " + msgFromServer);
        System.out.println("[CLIENT] Msg 1| Incoming message after decryption: " + decrptedMsgFromServer);

        //Send Acknowledgement message as 1rst message
        String ack = "ACK";
        byte[] encryptedACKMsg = Share.encrypts(ack.getBytes(), Share.clientEncrypt, Share.clientIVector, Share.clientMAC);
        clientOut.writeObject(encryptedACKMsg);//sends the ack msg
        System.out.println("[CLIENT] Msg 1| Acknowledgement message before encryption: " + ack);
        System.out.println("[CLIENT] Msg 1| Acknowledgement message after encryption: " + encryptedACKMsg);

        //Sends the seconds message
        String msgFromClient = "What is up?";
        byte[] encryptedMsgFromClient = Share.encrypts(msgFromClient.getBytes(), Share.clientEncrypt, Share.clientIVector, Share.clientMAC);
        clientOut.writeObject(encryptedMsgFromClient);//actually sends it
        System.out.println("[CLIENT] Msg 2| Outgoing message before encryption: " + msgFromClient);
        System.out.println("[CLIENT] Msg 2| Outgoing message after encryption: " + encryptedMsgFromClient);

        //Receive ack message from server
        byte[] acknowledgementFromServer = (byte[]) clientIn.readObject();

        //Decrypt message
        String decryptedAcknowledgementFromServer = Share.decrypts(acknowledgementFromServer, Share.serverEncrypt, Share.serverIVector, Share.serverMAC);
        System.out.println("[CLIENT] Msg 2| Acknowledgement before decryption: " + acknowledgementFromServer);
        System.out.println("[CLIENT] Msg 2| Acknowledgement after decryption: " + decryptedAcknowledgementFromServer);

        //check that the message sent to the client is the servers "ACK"
        if (!decryptedAcknowledgementFromServer.equals("ACK")){
            throw new RuntimeException("ACK not received.");
        }
    }//end of main bracket
}//end of class bracket