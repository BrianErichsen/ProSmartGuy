import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

// Class representing a DNS message
public class DNSMessage {
    // DNS message parts
    private DNSHeader header; // Header
    private ArrayList<DNSQuestion> questions; // List of questions
    private ArrayList<DNSRecord> answers; // List of answers
    private ArrayList<DNSRecord> authorityRecords; // List of authority records
    private ArrayList<DNSRecord> additionalRecords; // List of additional records
    private byte[] messageBytes; // Byte array containing that contains the message

    // Constructor that takes no arguments
    public DNSMessage() {
        // Initialize message parts
        this.header = new DNSHeader();
        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
        this.authorityRecords = new ArrayList<>();
        this.additionalRecords = new ArrayList<>();
        this.messageBytes = null;
    }

    // decodes a DNS message from byte array
    public static DNSMessage decodeMessage(byte[] bytes) throws IOException {
        DNSMessage dnsMessage = new DNSMessage();
        dnsMessage.messageBytes = Arrays.copyOf(bytes, bytes.length);
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)) {
            // Decodes the header
            dnsMessage.header = DNSHeader.decodeHeader(byteArrayInputStream);
            // Reads questions
            readQuestions(byteArrayInputStream, dnsMessage);
            // Reads records
            readRecords(byteArrayInputStream, dnsMessage.answers, dnsMessage.header.getAnswerCount(), dnsMessage);
            // Reads authority
            readRecords(byteArrayInputStream, dnsMessage.authorityRecords, dnsMessage.header.getAuthorityCount(), dnsMessage);
            // Reads additional records
            readRecords(byteArrayInputStream, dnsMessage.additionalRecords, dnsMessage.header.getAdditionalCount(), dnsMessage);
        }
        return dnsMessage;
    }

    // Reads questions from input stream
    private static void readQuestions(ByteArrayInputStream byteArrayInputStream, DNSMessage dnsMessage) throws IOException {
        int questionCount = dnsMessage.header.getQuestionCount();
        for (int i = 0; i < questionCount; i++) {
            DNSQuestion question = DNSQuestion.decodeQuestion(byteArrayInputStream, dnsMessage);
            dnsMessage.questions.add(question);
        }
    }
    // Reads records from input stream
    private static void readRecords(ByteArrayInputStream byteArrayInputStream, ArrayList<DNSRecord> records, int count, DNSMessage dnsMessage) throws IOException {
        for (int i = 0; i < count; i++) {
            DNSRecord record = DNSRecord.decodeRecord(byteArrayInputStream, dnsMessage);
            records.add(record);
        }
    }
    // Reads domain names
    public String[] readDomainName(InputStream inputStream) throws IOException {
        ArrayList<String> domainNameParts = new ArrayList<>();
        int currentByte = inputStream.read();
        // If the first byte is 0  returns an empty list
        if (currentByte == 0) {
            return new String[0];
        }
        while (currentByte > 0) {
            byte[] labelBytes = new byte[currentByte];
            inputStream.read(labelBytes, 0, labelBytes.length);
            String label = new String(labelBytes, StandardCharsets.UTF_8);
            domainNameParts.add(label);
            currentByte = inputStream.read();
        }
        return domainNameParts.toArray(new String[0]);
    }
    public String[] readDomainName(int firstByte) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(messageBytes, firstByte, messageBytes.length - firstByte);
        return readDomainName(byteArrayInputStream);
    }
    public static DNSMessage buildResponse(DNSMessage request, ArrayList<DNSRecord> answers) {
        DNSMessage response = new DNSMessage();
        response.answers = answers;
        response.header = DNSHeader.buildHeaderForResponse(request, response);
        response.questions = request.getQuestions();
        response.authorityRecords = request.getAuthorityRecords();
        response.additionalRecords = request.getAdditionalRecords();
        return response;
    }
    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HashMap<String, Integer> domainLocations = new HashMap<>();
        header.writeBytes(byteArrayOutputStream);
        for (DNSQuestion question : questions) {
            question.writeBytes(byteArrayOutputStream, domainLocations);
        }
        answers.get(0).writeBytes(byteArrayOutputStream, domainLocations);
        for (DNSRecord ar : authorityRecords) {
            ar.writeBytes(byteArrayOutputStream, domainLocations);
        }
        for (DNSRecord ad : additionalRecords) {
            ad.writeBytes(byteArrayOutputStream, domainLocations);
        }
        return byteArrayOutputStream.toByteArray();
    }
    public static void writeDomainName(ByteArrayOutputStream outputStream, HashMap<String, Integer> domainLocations, String[] domainPieces) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        String domainName = joinDomainName(domainPieces);
        if (domainLocations.containsKey(domainName)) {
            int location = domainLocations.get(domainName);
            location |= 0xC000;
            dataOutputStream.writeShort(location);
        } else {
            // Else, write domain name labels and update domainLocations
            Integer location = outputStream.size();
            domainLocations.put(domainName, location);
            for (String label : domainPieces) {
                dataOutputStream.writeByte(label.length());
                dataOutputStream.writeBytes(label);
            }
            // Write null byte
            dataOutputStream.writeByte(0);
        }
    }
    // join the pieces of a domain name with dots
    public static String joinDomainName(String[] pieces) {
        // Join domain name pieces with dots
        return String.join(".", pieces);
    }

    // Getters and setters
    public DNSHeader getHeader() {
        return header;
    }
    public ArrayList<DNSQuestion> getQuestions() {
        return questions;
    }
    public ArrayList<DNSRecord> getAnswers() {
        return answers;
    }
    public ArrayList<DNSRecord> getAuthorityRecords() {
        return authorityRecords;
    }
    public ArrayList<DNSRecord> getAdditionalRecords() {
        return additionalRecords;
    }
    public byte[] getMessageBytes() {
        return messageBytes;
    }
    public int getAnswerCount(){
        return answers.size();
    }
    @Override
    public String toString() {
        return "\nDNSMessage{" +
                "\nheader=" + header +
                "\n, questions=" + questions +
                "\n, answers=" + answers +
                "\n, authorityRecords=" + authorityRecords +
                "\n, additionalRecords=" + additionalRecords +
                '}';
    }
}//end of class bracket
