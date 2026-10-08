package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TcpDataStream2 {
    public static void main(String[] args) {
        String serverHost = "36.50.135.242"; // hoặc host server thi của bạn
        int serverPort = 2207;

        try (Socket socket = new Socket(serverHost, serverPort);
             DataInputStream dis = new DataInputStream(socket.getInputStream());
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

            // a. Gửi mã sinh viên và mã câu hỏi
            String code = "B23DCCN592;bn8JEjKQ"; // Thay mã câu hỏi tương ứng
            dos.writeUTF(code);
            dos.flush();

            // b. Nhận lần lượt chuỗi đã mã hóa và giá trị dịch chuyển s nguyên
            String encodedText = dis.readUTF();
            int s = dis.readInt();

            // c. Thực hiện giải mã thông điệp ban đầu
            String decodedText = decryptCaesar(encodedText, s);

            // Gửi chuỗi kết quả đã giải mã lên Server
            dos.writeUTF(decodedText);
            dos.flush();

            // d. Đóng socket được tự động xử lý bởi try-with-resources
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String decryptCaesar(String text, int s) {
        StringBuilder result = new StringBuilder();
        int shift = (s % 26 + 26) % 26; // Chuẩn hóa độ dịch trong khoảng [0, 25]

        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) {
                char original = (char) ('A' + (c - 'A' - shift + 26) % 26);
                result.append(original);
            } else if (Character.isLowerCase(c)) {
                char original = (char) ('a' + (c - 'a' - shift + 26) % 26);
                result.append(original);
            } else {
                result.append(c); // Giữ nguyên ký tự đặc biệt, số hoặc dấu cách nếu có
            }
        }

        return result.toString();
    }
}