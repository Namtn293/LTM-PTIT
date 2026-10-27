package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class UdpDataType2 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2207;

        // a. Gửi mã sinh viên và mã câu hỏi
        byte[] send = ";B23DCCN592;HmrgZZEp".getBytes();
        socket.send(new DatagramPacket(send, send.length, ip, port));

        // b. Nhận thông điệp từ server
        byte[] buf = new byte[4096];
        DatagramPacket dp = new DatagramPacket(buf, buf.length);
        socket.receive(dp);
        String[] s = new String(dp.getData(), 0, dp.getLength()).trim().split(";");

        // c. Tìm max, min và gửi lại theo định dạng requestId;max,min
        int[] a = Arrays.stream(s[1].split(",")).mapToInt(Integer::parseInt).sorted().toArray();
        byte[] out = (s[0] + ";" + a[a.length - 1] + "," + a[0]).getBytes();
        socket.send(new DatagramPacket(out, out.length, ip, port));

        // d. Đóng socket
        socket.close();
    }
}
