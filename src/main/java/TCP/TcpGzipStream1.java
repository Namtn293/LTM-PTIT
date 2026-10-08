package TCP;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TcpGzipStream1 {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2210);

        // syncFlush = true để flush được dữ liệu nén qua luồng mạng mà không cần đóng stream (finish)
        GZIPOutputStream gzipOut = new GZIPOutputStream(socket.getOutputStream(), true);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(gzipOut, StandardCharsets.UTF_8));

        // a. Gửi mã sinh viên và mã câu hỏi
        writer.write("B23DCCN592;KHp6PUX0\n");
        writer.flush();

        // b. Nhận dữ liệu từ server
        GZIPInputStream gzipIn = new GZIPInputStream(socket.getInputStream());
        byte[] buffer = new byte[4096];
        int bytesRead = gzipIn.read(buffer);
        String original = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8).trim();
        System.out.println("Nhan tu server: " + original);

        if (!original.isEmpty()) {
            // c. Đảo ngược chuỗi và mã hóa Base64
            String reversed = new StringBuilder(original).reverse().toString();
            String base64 = Base64.getEncoder().encodeToString(reversed.getBytes(StandardCharsets.UTF_8));

            String response = reversed + "|" + base64;
            System.out.println("Gui len server: " + response);

            // Gửi kết quả lên server
            writer.write(response + "\n");
            writer.flush();
        }

        // d. Đóng kết nối
        writer.close();
        gzipIn.close();
        socket.close();
    }
}