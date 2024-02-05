import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.IOException;
import java.net.*;
//Author: Brian Erichsen Fagundes
//DNS Server handler
public class DNSServer {
    private static final int PACKET_SIZE = 512;

    private static DNSCache cache = new DNSCache();
    private static ArrayList<DNSRecord> answer;

    public static void main(String[] args) {
        try {
            // Create a UDP socket to listen for requests on port 8053
            DatagramSocket socket = new DatagramSocket(8053);

            while (true) {
                try {
                    // Receive incoming request
                    byte[] requestData = new byte[PACKET_SIZE];
                    DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length);
                    socket.receive(requestPacket);

                    // Decode the DNS message
                    DNSMessage requestMessage = DNSMessage.decodeMessage(requestPacket.getData());
                    System.out.println("Received DNS request from: " + requestPacket.getAddress());

                    // Check cache for question
                    ArrayList<DNSQuestion> question = requestMessage.getQuestions();
                    DNSRecord cachedRecord = cache.query(question.get(0));
                    System.out.println("Cached record: " + cachedRecord);

                    // If question is in cache, set answer to cached answer
                    if (cachedRecord != null) {
                        ArrayList<DNSRecord> tempAnswer = new ArrayList<>();
                        tempAnswer.add(cachedRecord);
                        answer = tempAnswer;
                        System.out.println("Found cached record for query: " + question);
                    } else {
                        System.out.println("Not found in cached record for query: " + question);

                        // Forward request to Google DNS (8.8.8.8)
                        InetAddress googleDNS = InetAddress.getByName("8.8.8.8");
                        DatagramPacket forwardPacket = new DatagramPacket(requestData, requestData.length, googleDNS, 53);
                        socket.send(forwardPacket);
                        System.out.println("Forwarded request to Google DNS: " + googleDNS.getHostAddress());

                        // Receive response from Google DNS
                        byte[] responseFromGoogle = new byte[PACKET_SIZE];
                        DatagramPacket responsePacket = new DatagramPacket(responseFromGoogle, responseFromGoogle.length);
                        socket.receive(responsePacket);
                        System.out.println("Received response from Google DNS");

                        // Decode response from Google DNS
                        DNSMessage googleResponse = DNSMessage.decodeMessage(responsePacket.getData());
                        answer = googleResponse.getAnswers();
                        if (!answer.isEmpty()) {
                            cache.insert(question.get(0), answer.get(0));
                            System.out.println("Cached new record: " + question.get(0));
                        }
                        if (answer.isEmpty()) {
                            System.out.println("Error: faulty domain name");
                        }
                    }

                    // Build response message
                    DNSMessage responseMessage = DNSMessage.buildResponse(requestMessage, answer);
                    System.out.println("Response message: " + responseMessage);

                    // Send response back to client
                    byte[] responseData = responseMessage.toBytes();
                    DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length,
                            requestPacket.getAddress(), requestPacket.getPort());
                    socket.send(responsePacket);
                    System.out.println("Sent response to: " + requestPacket.getAddress());

                } catch (IOException e) {
                    // Handle IOException for each request individually
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            // Handle SocketException for socket creation
            e.printStackTrace();
        }
    }
}

