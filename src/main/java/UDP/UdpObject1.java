package UDP;

import java.io.*;
import java.net.*;

public class UdpObject1 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2209;

        // 1. Gửi request
        byte[] req = ";B23DCCN592;BuIOeDTd".getBytes();
        socket.send(new DatagramPacket(req, req.length, ip, port));

        // 2. Nhận gói tin & Deserialize từ byte thứ 8
        byte[] buf = new byte[4096];
        DatagramPacket dp = new DatagramPacket(buf, buf.length);
        socket.receive(dp);

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(dp.getData(), 8, dp.getLength() - 8));
        Student st = (Student) ois.readObject();

        // 3. Chuẩn hóa tên và tạo email
        String[] a = st.name.trim().toLowerCase().split("\\s+");
        String name = "", email = a[a.length - 1];
        for (int i = 0; i < a.length; i++) {
            name += Character.toUpperCase(a[i].charAt(0)) + a[i].substring(1) + " ";
            if (i < a.length - 1) email += a[i].charAt(0);
        }
        st.name = name.trim();
        st.email = email + "@ptit.edu.vn";

        // 4. Ghi 8 bytes RequestID + Object mới rồi gửi lại
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(dp.getData(), 0, 8);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(st);
        oos.flush();

        byte[] out = baos.toByteArray();
        socket.send(new DatagramPacket(out, out.length, ip, port));
        socket.close();
    }
}
