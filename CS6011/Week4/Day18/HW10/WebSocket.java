import java.io.IOException;
import java.nio.ByteBuffer;

public class WebSocket {

    public static void main(String[] args) {
        // Simulated WebSocket frame bytes
        byte[] frameBytes = { (byte) 0x81, (byte) 0x05, (byte) 0x48, (byte) 0x65, (byte) 0x6C, (byte) 0x6C, (byte) 0x6F };

        try {
            parseWebSocket(frameBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void parseWebSocket(byte[] frameBytes) throws IOException {
        byte firstByte = frameBytes[0];
        byte secondByte = frameBytes[1];

        int opcode = firstByte & 0x0F;
        boolean isMasked = (secondByte & 0x80) != 0;
        int payloadLength = secondByte & 0x7F;

        int payloadOffset = 2;

        if (payloadLength == 126) {
            // Read the next 2 bytes as a 16-bit integer for payload length
            payloadLength = ByteBuffer.wrap(frameBytes, payloadOffset, 2).getShort();
            payloadOffset += 2;
        } else if (payloadLength == 127) {
            // Read the next 8 bytes as a 64-bit integer for payload length
            payloadLength = (int) ByteBuffer.wrap(frameBytes, payloadOffset, 8).getLong();
            payloadOffset += 8;
        }

        byte[] maskingKey = null;

        if (isMasked) {
            // Read the next 4 bytes as the masking key
            maskingKey = new byte[4];
            System.arraycopy(frameBytes, payloadOffset, maskingKey, 0, 4);
            payloadOffset += 4;
        }

        byte[] payloadData = new byte[payloadLength];

        // Copy the payload data (masked or unmasked) into the payloadData array
        System.arraycopy(frameBytes, payloadOffset, payloadData, 0, payloadLength);

        // If the frame is masked, unmask the payload data using the masking key
        if (isMasked) {
            for (int i = 0; i < payloadLength; i++) {
                payloadData[i] ^= maskingKey[i % 4];
            }
        }

        // Now you have the opcode, payload length, and payload data
        System.out.println("Opcode: " + opcode);
        System.out.println("Payload Length: " + payloadLength);
        System.out.println("Payload Data: " + new String(payloadData));
    }
}

