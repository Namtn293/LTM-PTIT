package UDP;

import java.io.*;
import java.net.*;

public class UdpObject2 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress ip = InetAddress.getByName("36.50.135.242");
        int port = 2209;

        // 1. Gửi request
        byte[] req = ";B23DCCN592;kPA4fv0h".getBytes();
        socket.send(new DatagramPacket(req, req.length, ip, port));

        // 2. Nhận gói tin & Deserialize từ byte thứ 8
        byte[] buf = new byte[4096];
        DatagramPacket dp = new DatagramPacket(buf, buf.length);
        socket.receive(dp);

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(dp.getData(), 8, dp.getLength() - 8));
        Customer c = (Customer) ois.readObject();

        // 3. Xử lý logic
        // a. Tên: TÊN, Họ Đệm (vd: HAI, Nguyen Van)
        String[] a = c.getName().trim().toLowerCase().split("\\s+");
        String name = a[a.length - 1].toUpperCase() + ",";
        for (int i = 0; i < a.length - 1; i++) {
            name += " " + Character.toUpperCase(a[i].charAt(0)) + a[i].substring(1);
        }
        c.setName(name);

        // b. Ngày sinh: MM-dd-yyyy -> dd/MM/yyyy
        String[] d = c.getDayOfBirth().split("-");
        c.setDayOfBirth(d[1] + "/" + d[0] + "/" + d[2]);

        // c. Username: chữ cái đầu họ đệm + tên (vd: nvhai)
        String user = "";
        for (int i = 0; i < a.length - 1; i++) user += a[i].charAt(0);
        c.setUserName(user + a[a.length - 1]);

        // 4. Ghi 8 bytes RequestID + Object mới rồi gửi lại
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(dp.getData(), 0, 8);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(c);
        oos.flush();

        byte[] out = baos.toByteArray();
        socket.send(new DatagramPacket(out, out.length, ip, port));
        socket.close();
    }
}
