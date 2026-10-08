package TCP;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpByteStream2 {
    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("36.50.135.242", 2206);

        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        System.out.println(1);
        outputStream.write("B23DCCN592;ueDFJMbw".getBytes());
        outputStream.flush();
        System.out.println(2);


        byte[] bytes=new byte[1024];
        int bytesH=inputStream.read(bytes);
        System.out.println(3);

        String[] s = new String(bytes, 0, bytesH, StandardCharsets.UTF_8).trim().split(",");

        System.out.println(4);

        int max1 = Integer.MIN_VALUE, vt1 = -1;
        int max2 = Integer.MIN_VALUE, vt2 = -1;

        for (int i = 0; i < s.length; i++) {
            int val = Integer.parseInt(s[i].trim());

            if (val > max1) {
                // Đẩy max1 cũ xuống làm max2
                max2 = max1;
                vt2 = vt1;

                // Cập nhật max1 mới
                max1 = val;
                vt1 = i;
            } else if (val > max2 && val < max1) {
                max2 = val;
                vt2 = i;
            }
        }

        String s3 = max2 + "," + vt2;

        outputStream.write(s3.getBytes());
        outputStream.flush();

        socket.close();
    }
}