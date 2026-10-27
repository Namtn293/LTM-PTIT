package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpString1 {
        public static void main(String[] args) throws Exception {
            DatagramSocket socket = new DatagramSocket();
            InetAddress ip = InetAddress.getByName("36.50.135.242");
            int port = 2208;

            // a. Gửi mã sinh viên và mã câu hỏi
            byte[] send = ";B23DCCN592;8i0rs0oA".getBytes();
            socket.send(new DatagramPacket(send, send.length, ip, port));

            // b. Nhận thông điệp từ server (không trim s[1] để giữ nguyên vị trí)
            byte[] buf = new byte[4096];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            socket.receive(dp);
            String[] s = new String(dp.getData(), 0, dp.getLength()).split(";", 2);
            String data = s[1];

            // c. Tìm ký tự xuất hiện nhiều nhất (bỏ qua khoảng trắng) và vị trí
            int[] cnt = new int[256];
            for (char c : data.toCharArray()) {
                if (c != ' ') cnt[c]++;
            }

            char maxChar = 0;
            int maxCnt = 0;
            for (char c : data.toCharArray()) {
                if (c != ' ' && cnt[c] > maxCnt) {
                    maxCnt = cnt[c];
                    maxChar = c;
                }
            }

            StringBuilder res = new StringBuilder(s[0] + ";" + maxChar + ":");
            for (int i = 0; i < data.length(); i++) {
                if (data.charAt(i) == maxChar) res.append(i + 1).append(",");
            }

            byte[] out = res.toString().getBytes();
            socket.send(new DatagramPacket(out, out.length, ip, port));

            // d. Đóng socket
            socket.close();
        }
}
