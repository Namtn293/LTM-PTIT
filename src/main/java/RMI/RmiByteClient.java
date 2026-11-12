package RMI;

import java.rmi.Naming;
import java.util.LinkedHashMap;
import java.util.Map;

public class RmiByteClient {
    public static void main(String[] args) throws Exception {
        String studentCode = "B23DCCN592";
        String qCode = "Gg5Cqx8u";

        ByteService service = (ByteService) Naming.lookup("rmi://36.50.135.242/RMIByteService");

        // a. Nhận mảng byte[] từ server
        byte[] data = service.requestData(studentCode, qCode);
        if (data == null || data.length == 0) {
            System.err.println("Server tra ve null/rong - kiem tra lai studentCode hoac qCode!");
            return;
        }

        // b. Tìm phần tử xuất hiện ít nhất (nếu bằng nhau lấy phần tử xuất hiện đầu tiên)
        Map<Byte, Integer> freq = new LinkedHashMap<>();
        for (byte b : data) {
            freq.put(b, freq.getOrDefault(b, 0) + 1);
        }

        byte minByte = 0;
        int minCount = 0;
        for (Map.Entry<Byte, Integer> entry : freq.entrySet()) {
            if (entry.getValue() > minCount) {
                minCount = entry.getValue();
                minByte = entry.getKey();
            }
        }

        // c. Gửi mảng byte kết quả [giá trị, số lần xuất hiện] trở lại server
        byte[] result = new byte[]{minByte, (byte) minCount};
        service.submitData(studentCode, qCode, result);
    }
}

