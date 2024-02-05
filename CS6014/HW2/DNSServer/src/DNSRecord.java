import java.io.*;
import java.util.Date;
import java.util.HashMap;
public class DNSRecord {
    private String[] name;
    private int type;
    private int rclass;
    private int ttl;
    private int rdLength;
    private byte[] rdata;
    private Date creationDate;
    //Decodes  DNS record from the input
    static DNSRecord decodeRecord(InputStream inputStream, DNSMessage dnsMessage){
        DNSRecord record = new DNSRecord();
        record.creationDate = new Date();
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            inputStream.mark(2);
            int firstByte = dataInputStream.readShort();
            if (isPtrToPrior(firstByte)) {
                int pointer= firstByte & (short) 0x3FFF ;
                record.name = dnsMessage.readDomainName(pointer);
            } else {
                inputStream.reset();
                record.name = dnsMessage.readDomainName(inputStream);
            }
            record.type = dataInputStream.readShort();
            record.rclass = dataInputStream.readShort();
            record.ttl = dataInputStream.readInt();
            record.rdLength = dataInputStream.readShort(); //This field contains the length of the RDATA field

            record.rdata = new byte[record.rdLength];
            dataInputStream.readFully(record.rdata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return record;
    }
    // Checks if pointer to a prior
    static Boolean isPtrToPrior(int labelLength) {
        return (labelLength & 0xC000) == 0xC000;
    }
    public void writeBytes(ByteArrayOutputStream outputStream, HashMap<String, Integer> domainNameLocations) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        DNSMessage.writeDomainName(outputStream, domainNameLocations, name);
        dataOutputStream.writeShort(type);
        dataOutputStream.writeShort(rclass);
        dataOutputStream.writeInt(ttl);
        dataOutputStream.writeShort(rdLength);
        if(rdLength > 0) {
            dataOutputStream.write(rdata);
        }
    }
    public boolean isExpired() {
        // Finds current date and time
        Date now = new Date();
        // Finds the current time in ms
        long currentTimeMillis = now.getTime();
        long expirationTimeMillis = creationDate.getTime() + (ttl * 1000);
        return currentTimeMillis > expirationTimeMillis;
    }
    @Override
    public String toString() {
        return "DNSRecord{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", rclass=" + rclass +
                ", ttl=" + ttl +
                ", rdLength=" + rdLength +
                ", rdata=" + new String(rdata) +
                ", creationDate=" + creationDate +
                '}';
    }
}//end of class bracket

