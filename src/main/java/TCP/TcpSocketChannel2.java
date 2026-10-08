package TCP;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TcpSocketChannel2 {

    // Đọc đủ số lượng byte vào buffer (readFully)
    public static void readFully(SocketChannel channel, ByteBuffer buffer) throws IOException {
        while (buffer.hasRemaining()) {
            int bytesRead = channel.read(buffer);
            if (bytesRead == -1) {
                throw new EOFException("Ket noi bi dong truoc khi doc du du lieu!");
            }
        }
    }

    // Gửi frame: 4 byte độ dài (int32) + payload (UTF-8)
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

    // Đọc 1 frame: đọc 4 byte độ dài trước, sau đó đọc đúng payload
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

    // Trích xuất trường kiểu chuỗi trong JSON
    private static String extractJsonString(String json, String field) {
        Pattern pattern = Pattern.compile("\"" + Pattern.quote(field) + "\"\\s*:\\s*\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    // Trích xuất trường boolean (true=1, false=0) trong JSON
    private static String extractJsonBoolean(String json, String field) {
        Pattern pattern = Pattern.compile("\"" + Pattern.quote(field) + "\"\\s*:\\s*(true|false|1|0)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            String val = matcher.group(1);
            return (val.equalsIgnoreCase("true") || val.equals("1")) ? "1" : "0";
        }
        return "0";
    }

    public static void main(String[] args) {
        String serverHost = "36.50.135.242";
        int serverPort = 2211;

        String studentCode = "B23DCCN592";
        String qCode = "ORhzzX5p"; // Lưu ý: Chữ O hoa, không phải số 0

        try (SocketChannel channel = SocketChannel.open()) {
            channel.connect(new InetSocketAddress(serverHost, serverPort));

            // a. Gửi mã sinh viên và mã câu hỏi: studentCode;qCode
            String request = studentCode + ";" + qCode;
            sendFrame(channel, request);
            System.out.println("Da gui: " + request);

            // b. Nhận dữ liệu từ server gồm đúng 2 frame liên tiếp
            String frame1 = readFrame(channel);
            String frame2 = readFrame(channel);
            String fullJson = frame1 + frame2;
            System.out.println("Full JSON nhan duoc: " + fullJson);

            // c. Trích xuất event, user, ok
            String event = extractJsonString(fullJson, "event");
            String user = extractJsonString(fullJson, "user");
            String ok = extractJsonBoolean(fullJson, "ok");

            // Định dạng: event=<event>;user=<user>;ok=<0|1>
            String response = "event=" + event + ";user=" + user + ";ok=" + ok;
            System.out.println("Gui len server: " + response);

            sendFrame(channel, response);

            // d. Đóng kết nối
            System.out.println("Hoan thanh va dong ket noi!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
