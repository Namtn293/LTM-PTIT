package TCP;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class TcpSocketChannel1 {

    // Đọc đủ số lượng byte vào buffer (readFully)
    public static void readFully(SocketChannel channel, ByteBuffer buffer) throws IOException {
        while (buffer.hasRemaining()) {
            int bytesRead = channel.read(buffer);
            if (bytesRead == -1) {
                throw new EOFException("Ket noi bi dong truoc khi doc du du lieu!");
            }
        }
    }

    // Gửi một frame: 4 byte độ dài (int32) + payload (UTF-8)
    public static void sendFrame(SocketChannel channel, String message) throws IOException {
        byte[] payload = message.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(4 + payload.length);
        buffer.putInt(payload.length);
        buffer.put(payload);
        buffer.flip();
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

    // Đọc một frame: đọc 4 byte độ dài trước, sau đó đọc đúng payload
    public static String readFrame(SocketChannel channel) throws IOException {
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        readFully(channel, lengthBuffer);
        lengthBuffer.flip();
        int length = lengthBuffer.getInt();

        ByteBuffer payloadBuffer = ByteBuffer.allocate(length);
        readFully(channel, payloadBuffer);
        payloadBuffer.flip();
        return StandardCharsets.UTF_8.decode(payloadBuffer).toString();
    }

    public static void main(String[] args) {
        String serverHost = "36.50.135.242";
        int serverPort = 2211;

        // Thay mã sinh viên và mã câu hỏi ở đây
        String studentCode = "B23DCCN592";
        String qCode = "cBT6cUY7"; // Thay mã câu hỏi tương ứng

        try (SocketChannel channel = SocketChannel.open()) {
            channel.connect(new InetSocketAddress(serverHost, serverPort));

            // a. Gửi mã sinh viên và mã câu hỏi: studentCode;qCode
            String request = studentCode + ";" + qCode;
            sendFrame(channel, request);
            System.out.println("Da gui: " + request);

            // b. Nhận đúng 3 frame liên tiếp và nối lại thành HTTP request hoàn chỉnh
            StringBuilder httpBuilder = new StringBuilder();
            for (int i = 1; i <= 3; i++) {
                String framePayload = readFrame(channel);
                System.out.println("Nhan frame " + i + " (" + framePayload.length() + " ky tu): " + framePayload);
                httpBuilder.append(framePayload);
            }
            String fullHttpRequest = httpBuilder.toString();
            System.out.println("--- Full HTTP Request ---");
            System.out.println(fullHttpRequest);

            // c. Trích xuất METHOD, PATH, HOST
            // Request line: METHOD PATH HTTP_VERSION
            String[] lines = fullHttpRequest.split("\r?\n");
            String requestLine = lines[0].trim();
            String[] parts = requestLine.split("\\s+");
            String method = parts[0];
            String path = parts[1]; // PATH bao gồm cả query-string

            // Tìm header Host
            String host = "";
            for (String line : lines) {
                if (line.toLowerCase().startsWith("host:")) {
                    host = line.substring(line.indexOf(':') + 1).trim();
                    break;
                }
            }

            // Định dạng: METHOD;PATH;HOST
            String response = method + ";" + path + ";" + host;
            System.out.println("Gui len server: " + response);

            sendFrame(channel, response);

            // d. Đóng kết nối
            System.out.println("Hoan thanh va dong ket noi!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
