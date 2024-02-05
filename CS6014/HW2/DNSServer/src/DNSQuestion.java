import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
//This class represents a DNS Questions
public class DNSQuestion {

    protected String[] qname; //example.com or google.com
    protected int qtype;
    protected int qclass;
    public DNSQuestion() {
        this.qname = new String[]{""};
        this.qtype = 0;
        this.qclass = 0;
    }
    // Decodes DNS question
    static DNSQuestion decodeQuestion(InputStream inputStream, DNSMessage dnsMessage) throws IOException {
        DNSQuestion question = new DNSQuestion();
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            question.qname = dnsMessage.readDomainName(inputStream);
            question.qtype = dataInputStream.readShort();
            question.qclass = dataInputStream.readShort();
            return question;
        }
    }
    // Write the question bytes to be sent to the client.
    void writeBytes(ByteArrayOutputStream byteArrayOutputStream, HashMap<String, Integer> domainNameLocations) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        DNSMessage.writeDomainName(byteArrayOutputStream, domainNameLocations, qname);
        dataOutputStream.writeShort(qtype);
        dataOutputStream.writeShort(qclass);
    }
    @Override
    public String toString() {
        return "DNSQuestion{" +
                "qname=" + Arrays.toString(qname) +
                ", qtype=" + qtype +
                ", qclass=" + qclass +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DNSQuestion that = (DNSQuestion) o;
        return qtype == that.qtype &&
                qclass == that.qclass &&
                Arrays.equals(qname, that.qname);
    }
    @Override
    public int hashCode() {
        int result = Objects.hash(qtype, qclass);
        result = 31 * result + Arrays.hashCode(qname);
        return result;
    }
}//end of class bracket


