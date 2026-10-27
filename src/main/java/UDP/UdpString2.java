package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpString2 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2208;

        // a. Gửi mã sinh viên và mã câu hỏi
        byte[] send = ";B23DCCN592;Gm7klA6J".getBytes();
        socket.send(new DatagramPacket(send, send.length, ip, port));

        // b. Nhận thông điệp từ server
        byte[] buf = new byte[4096];
        DatagramPacket dp = new DatagramPacket(buf, buf.length);
        socket.receive(dp);
        String[] s = new String(dp.getData(), 0, dp.getLength()).trim().split(";", 2);

        // c. Chuẩn hóa chuỗi (viết hoa chữ cái đầu mỗi từ, còn lại viết thường)
        String[] words = s[1].trim().split("\\s+");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
        }

        byte[] out = (s[0] + ";" + String.join(" ", words)).getBytes();
        socket.send(new DatagramPacket(out, out.length, ip, port));

        // d. Đóng socket
        socket.close();
    }
}
