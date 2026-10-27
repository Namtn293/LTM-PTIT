package UDP;

public class UdpObjectTemplate {
//    // B1. Gửi mã SV & mã câu hỏi
//    byte[] req = ";MãSV;MãCH".getBytes();
//    socket.send(new DatagramPacket(req, req.length, ip, port));
//
//    // B2. Nhận và lấy Object (bỏ qua 8 bytes đầu)
//    byte[] buf = new byte[4096];
//    DatagramPacket dp = new DatagramPacket(buf, buf.length);
//    socket.receive(dp);
//    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(dp.getData(), 8, dp.getLength() - 8));
//    X x = (X) ois.readObject();
//
//// B3. Xử lý yêu cầu đề bài ...
//
//    // B4. Ghi 8 bytes RequestID vào trước, rồi ghi Object mới và gửi
//    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//    baos.write(dp.getData(), 0, 8);
//    ObjectOutputStream oos = new ObjectOutputStream(baos);
//    oos.writeObject(x);
//    oos.flush();
//    byte[] out = baos.toByteArray();
//    socket.send(new DatagramPacket(out, out.length, ip, port));
//    socket.close();

}
