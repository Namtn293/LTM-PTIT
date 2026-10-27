package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

public class UdpDataType1 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2207;

        // a. Gửi mã sinh viên và mã câu hỏi
        byte[] send = ";B23DCCN592;KgCI3sz0".getBytes();
        socket.send(new DatagramPacket(send, send.length, ip, port));

        // b. Nhận thông điệp từ server
        byte[] buf = new byte[4096];
        DatagramPacket dp = new DatagramPacket(buf, buf.length);
        socket.receive(dp);
        String[] s = new String(dp.getData(), 0, dp.getLength()).trim().split(";");

        // c. Tìm các số còn thiếu từ 1 đến n và gửi lại
        int n = Integer.parseInt(s[1]);
        Set<String> set = new HashSet<>(Arrays.asList(s[2].split(",")));
        List<String> res = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (!set.contains(i + "")) res.add(i + "");
        }

        byte[] out = (s[0] + ";" + String.join(",", res)).getBytes();
        socket.send(new DatagramPacket(out, out.length, ip, port));

        // d. Đóng socket
        socket.close();
    }
}
