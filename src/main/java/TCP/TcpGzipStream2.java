package TCP;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TcpGzipStream2 {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2210);

        // syncFlush = true để đẩy dữ liệu nén qua luồng mạng mà không đóng luồng
        GZIPOutputStream gzipOut = new GZIPOutputStream(socket.getOutputStream(), true);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(gzipOut, StandardCharsets.UTF_8));

        // a. Gửi mã sinh viên và mã câu hỏi: studentCode;qCode + \n
        String studentCode = "B23DCCN592";
        String qCode = "oTzAVkU3"; // Thay mã câu hỏi tương ứng

        writer.write(studentCode + ";" + qCode + "\n");
        writer.flush();
        System.out.println("Da gui len server: " + studentCode + ";" + qCode);

        // b. Nhận dữ liệu từ server và giải nén
        GZIPInputStream gzipIn = new GZIPInputStream(socket.getInputStream());
        byte[] buffer = new byte[4096];
        int bytesRead = gzipIn.read(buffer);
        String original = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8).trim();
        System.out.println("Nhan tu server: " + original);

        if (!original.isEmpty()) {
            // c. Sắp xếp các ký tự trong chuỗi nhận được tăng dần theo mã ASCII
            char[] chars = original.toCharArray();
            Arrays.sort(chars);
            String sorted = new String(chars);
            System.out.println("Chuoi sau khi sap xep: " + sorted);

            // Gửi chuỗi kết quả đã sắp xếp lên server kèm theo ký tự xuống dòng '\n'
            writer.write(sorted + "\n");
            writer.flush();
            System.out.println("Da gui ket qua len server!");
        }

        // d. Đóng kết nối và kết thúc chương trình
        writer.close();
        gzipIn.close();
        socket.close();
        System.out.println("Dong ket noi thanh cong!");
    }
}
