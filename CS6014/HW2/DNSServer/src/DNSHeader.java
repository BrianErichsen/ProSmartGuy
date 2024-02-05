import java.io.*;
import java.util.Objects;


public class DNSHeader {

    private int id;
    protected Short flags;
    protected int qr; //0 for query and 1 for response
    protected int opCode; //type of query, 0 for standard and 1 for other
    protected int aa; // server authoritative
    protected int tc; //truncated msg
    protected int rd; //recursive
    protected int ra; //if supports queries
    protected int z; // 0

    protected int rCode; //tells result of query; NoError(0), FormatError(1), ...
    private int questionCount; //# of entries question section
    private int answerCount; //# of entries in the answer section
    private int authorityCount; //# of entries in the athority section
    private int additionalCount; //# of entries in the additional section

    //no arguments constructor
    protected DNSHeader() {
        //set default header data to 0
        this.id = 0;
        this.flags = 0;
        this.qr =0;
        this.opCode = 0;
        this.aa =0;
        this.tc = 0;
        this.rd = 0;
        this.ra = 0;
        this.z = 0;
        this.rCode = 0;
        this.questionCount = 0;
        this.answerCount = 0;
        this.authorityCount = 0;
        this.additionalCount = 0;
    }

    // Decode the header from a byte array input stream
    public static DNSHeader decodeHeader(ByteArrayInputStream byteArrayInputStream) throws IOException {
        //Initializes the object to be returned
        DNSHeader header = new DNSHeader();
        //Reads 2 bytes at time and set proper fields
        try (DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream)) {
            header.id = dataInputStream.readShort();
            header.flags = dataInputStream.readShort();
            parseFlags(header.flags, header);
            header.questionCount = dataInputStream.readShort();
            header.answerCount = dataInputStream.readShort();
            header.authorityCount = dataInputStream.readShort();
            header.additionalCount = dataInputStream.readShort();
        }
        //returns new modified object
        return header;
    }
    //helper method to parse the flags
    public static void parseFlags(Short flags, DNSHeader header) {
        // qr flag
        header.qr = (flags >> 15) & 0x1;
        // opcode flag
        header.opCode = (flags >> 11) & 0xF;
        // aa flag
        header.aa = (flags >> 10) & 0x1;
        // tc flag
        header.tc = (flags >> 9) & 0x1;
        // rd flag
        header.rd = (flags >> 8) & 0x1;
        // ra flag
        header.ra = (flags >> 7) & 0x1;
        // rcode flag
        header.rCode = flags & 0xF;
    }
    // Builds DNSHeader for the response
    public static DNSHeader buildHeaderForResponse(DNSMessage request, DNSMessage response) {
        DNSHeader requestHeader = request.getHeader();
        // Create a new header using the request's header as a template
        DNSHeader responseHeader = new DNSHeader();
        responseHeader.id = (requestHeader.getId());
        responseHeader.flags = (requestHeader.getFlags());
        responseHeader.questionCount = (requestHeader.getQuestionCount());
        responseHeader.authorityCount = (requestHeader.getAuthorityCount());
        responseHeader.additionalCount = (requestHeader.getAdditionalCount());
        // Set QR flag to indicate a response
        responseHeader.qr = 1;
        // Set ANCOUNT to 1 if answer is good (otherwise so not send an
        if (response.getAnswerCount() > 0) {

            responseHeader.answerCount = 1;
        }
        return responseHeader;
    }
    //Encodes the header to bytes
    public void writeBytes(ByteArrayOutputStream outputStream) throws IOException {
        //uses input and converts to dataOutputStream type for class methods
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        //writes short bytes for each field
        dataOutputStream.writeShort(id);
        //converts the flags to short
        flags = convertFlagsToShort();
        dataOutputStream.writeShort(flags);
        dataOutputStream.writeShort(questionCount);
        dataOutputStream.writeShort(answerCount);
        dataOutputStream.writeShort(authorityCount);
        dataOutputStream.writeShort(additionalCount);
    }

    // convert flag int into two bytes (short)
    public short convertFlagsToShort() {
        short result = 0;
        result |= (short) ((qr & 0x1) << 15);
        result |= (short) ((opCode & 0xF) << 11);
        result |= (short) ((aa & 0x1) << 10);
        result |= (short) ((tc & 0x1) << 9);
        result |= (short) ((rd & 0x1) << 8);
        result |= (short) ((ra & 0x1) << 7);
        result |= (short) ((z & 0x7) << 4);
        result |= (short) (rCode & 0xF);

        return result;
    }
    //Return a human-readable string version of a header object.
    @Override
    public String toString() {
        return "DNSHeader{" +
                "id=" + id +
                ", flags=" + flags +
                ", qdcount=" + questionCount +
                ", ancount=" + answerCount +
                ", nscount=" + authorityCount +
                ", arcount=" + additionalCount +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DNSHeader dnsHeader = (DNSHeader) o;
        return id == dnsHeader.id && qr == dnsHeader.qr && opCode == dnsHeader.opCode && aa == dnsHeader.aa && tc == dnsHeader.tc && rd == dnsHeader.rd && ra == dnsHeader.ra && z == dnsHeader.z && rCode == dnsHeader.rCode && questionCount == dnsHeader.questionCount && answerCount == dnsHeader.answerCount && authorityCount == dnsHeader.authorityCount && additionalCount == dnsHeader.additionalCount && Objects.equals(flags, dnsHeader.flags);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, flags, qr, opCode, aa, tc, rd, ra, z, rCode, questionCount, answerCount, authorityCount, additionalCount);
    }
    //Getter Functions
    public int getId() {
        return id;
    }

    public short getFlags() {
        return flags;
    }

    public int getQr() {
        return qr;
    }

    public int getOpCode() {
        return opCode;
    }

    public int getAa() {
        return aa;
    }

    public int getTc() {
        return tc;
    }

    public int getRd() {
        return rd;
    }

    public int getRa() {
        return ra;
    }

    public int getRCode() {
        return rCode;
    }
    public int getZ() {
        return z;
    }
    public int getQuestionCount() {
        return questionCount;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public int getAuthorityCount() {
        return authorityCount;
    }

    public int getAdditionalCount() {
        return additionalCount;
    }
}//end of class bracket

