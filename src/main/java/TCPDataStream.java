import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TCPDataStream {
    public static void main(String[] args) {
        String serverIp = "36.50.135.242";
        int port = 2207;

        String studentCode = "B23DCCN592";
        String qCode = "bn8JEjKQ";

        try (Socket socket = new Socket(serverIp, port)) {
            socket.setSoTimeout(5000);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // a. Gửi mã sinh viên và mã câu hỏi
            dos.writeUTF(studentCode + ";" + qCode);
            dos.flush();

            // b. Nhận chuỗi đã mã hóa và giá trị dịch chuyển s
            String encryptedText = dis.readUTF();
            int s = dis.readInt();

            // c. Giải mã chuỗi: dịch ngược lại s vị trí
            int shift = s % 26;
            StringBuilder decrypted = new StringBuilder();

            for (int i = 0; i < encryptedText.length(); i++) {
                char c = encryptedText.charAt(i);

                if (c >= 'A' && c <= 'Z') {
                    // Dịch ngược trong bảng chữ cái in hoa
                    char original = (char) ((c - 'A' - shift + 26) % 26 + 'A');
                    decrypted.append(original);
                } else if (c >= 'a' && c <= 'z') {
                    // Dịch ngược trong bảng chữ cái in thường
                    char original = (char) ((c - 'a' - shift + 26) % 26 + 'a');
                    decrypted.append(original);
                } else {
                    // Ký tự số hoặc ký tự đặc biệt giữ nguyên
                    decrypted.append(c);
                }
            }

            // Gửi kết quả giải mã lên server
            dos.writeUTF(decrypted.toString());
            dos.flush();

            // d. Đóng kết nối (được xử lý tự động bởi try-with-resources)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}