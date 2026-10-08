package TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Bài toán giả định: Tìm dãy con tăng ngặt dài nhất và gửi kết quả về Server qua giao thức TCP.
 * 
 * Chương trình hỗ trợ 2 trường hợp:
 * 1. Dãy con tăng ngặt không cần liên tiếp (LIS - Longest Increasing Subsequence).
 * 2. Đoạn con liên tiếp tăng ngặt dài nhất (Longest Contiguous Increasing Subarray).
 * 
 * Tích hợp sẵn MockServer nội bộ để chạy thử nghiệm trực tiếp trên máy không cần server thật.
 */
public class DayTangNgatDaiNhat {

    // Đặt thành false nếu kết nối đến Server thật (ví dụ Server chấm thi)
    private static final boolean RUN_WITH_MOCK_SERVER = true;

    private static final String SERVER_HOST = "36.50.135.242";
    private static final int SERVER_PORT = 2208; // Cổng dịch vụ (ví dụ: 2208 với Character Stream)
    private static final String STUDENT_CODE = "B23DCCN592";
    private static final String QUESTION_CODE = "TEST_LIS";

    public static void main(String[] args) {
        int port = SERVER_PORT;
        String host = SERVER_HOST;

        if (RUN_WITH_MOCK_SERVER) {
            port = 8888;
            host = "localhost";
            // Khởi động Mock Server chạy ngầm để test giả lập
            startMockServer(port);
        }

        try {
            // 1. Tạo kết nối TCP Socket tới Server
            System.out.println("[Client] Đang kết nối tới " + host + ":" + port + "...");
            Socket socket = new Socket(host, port);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // 2. Gửi mã sinh viên và mã câu hỏi: "studentCode;qCode\n"
            String request = STUDENT_CODE + ";" + QUESTION_CODE + "\n";
            writer.write(request);
            writer.flush();
            System.out.println("[Client] Đã gửi: " + request.trim());

            // 3. Nhận dữ liệu từ Server (Ví dụ Server trả về chuỗi các số phân tách bởi dấu phẩy hoặc khoảng trắng)
            String response = reader.readLine();
            System.out.println("[Client] Đã nhận từ Server: " + response);

            if (response != null && !response.trim().isEmpty()) {
                // Parse chuỗi thành danh sách số nguyên
                // Hỗ trợ cả phân tách bằng dấu phẩy ", " hoặc khoảng trắng "\\s+"
                String[] tokens = response.split("[,\\s]+");
                List<Integer> numbers = new ArrayList<>();
                for (String token : tokens) {
                    if (!token.trim().isEmpty()) {
                        numbers.add(Integer.parseInt(token.trim()));
                    }
                }

                // =========================================================================
                // TRƯỜNG HỢP 1: Dãy con tăng ngặt KHÔNG CẦN LIÊN TIẾP (LIS - Subsequence)
                // =========================================================================
                List<Integer> lis = findLIS(numbers);
                System.out.println("[Client] -> LIS (không cần liên tiếp): " + lis + " (Độ dài: " + lis.size() + ")");

                // =========================================================================
                // TRƯỜNG HỢP 2: Đoạn con LIÊN TIẾP tăng ngặt dài nhất (Subarray)
                // =========================================================================
                List<Integer> contiguous = findLongestContiguousSubarray(numbers);
                System.out.println("[Client] -> Đoạn con liên tiếp tăng dài nhất: " + contiguous + " (Độ dài: " + contiguous.size() + ")");

                // 4. Chuẩn bị kết quả gửi về Server
                // Tùy theo yêu cầu của đề bài, có thể gửi:
                // - Cách 1: "độ_dài;phần_tử_1,phần_tử_2,..." (mặc định)
                // - Cách 2: Chỉ gửi độ dài "6"
                // - Cách 3: Chỉ gửi danh sách số "10,22,33,50,60,80"
                
                // Ở đây ta gửi theo chuẩn: độ_dài;dãy_số
                StringBuilder sb = new StringBuilder();
                sb.append(lis.size()).append(";");
                for (int i = 0; i < lis.size(); i++) {
                    sb.append(lis.get(i));
                    if (i < lis.size() - 1) sb.append(",");
                }
                sb.append("\n");

                writer.write(sb.toString());
                writer.flush();
                System.out.println("[Client] Đã gửi kết quả về Server: " + sb.toString().trim());
            }

            // 5. Đóng socket
            socket.close();
            System.out.println("[Client] Kết thúc phiên kết nối.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Thuật toán 1: Tìm dãy con tăng ngặt dài nhất (LIS - Longest Increasing Subsequence)
     * Độ phức tạp: O(N^2) kèm truy vết lấy danh sách phần tử.
     */
    public static List<Integer> findLIS(List<Integer> a) {
        int n = a.size();
        if (n == 0) return Collections.emptyList();

        int[] dp = new int[n];      // dp[i]: độ dài LIS kết thúc tại chỉ số i
        int[] trace = new int[n];   // trace[i]: chỉ số của phần tử đứng trước a[i] trong LIS

        Arrays.fill(dp, 1);
        Arrays.fill(trace, -1);

        int maxLen = 1;
        int lastIndex = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // Tăng ngặt: a.get(j) < a.get(i)
                if (a.get(j) < a.get(i) && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    trace[i] = j;
                }
            }
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                lastIndex = i;
            }
        }

        // Truy vết lại dãy số
        List<Integer> result = new ArrayList<>();
        int curr = lastIndex;
        while (curr != -1) {
            result.add(a.get(curr));
            curr = trace[curr];
        }
        Collections.reverse(result);
        return result;
    }

    /**
     * Thuật toán 2: Tìm đoạn con LIÊN TIẾP tăng ngặt dài nhất (Longest Contiguous Increasing Subarray)
     * Độ phức tạp: O(N)
     */
    public static List<Integer> findLongestContiguousSubarray(List<Integer> a) {
        int n = a.size();
        if (n == 0) return Collections.emptyList();

        int maxLen = 1;
        int bestStart = 0;

        int currentLen = 1;
        int currentStart = 0;

        for (int i = 1; i < n; i++) {
            if (a.get(i) > a.get(i - 1)) {
                currentLen++;
            } else {
                currentLen = 1;
                currentStart = i;
            }

            if (currentLen > maxLen) {
                maxLen = currentLen;
                bestStart = currentStart;
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int i = bestStart; i < bestStart + maxLen; i++) {
            result.add(a.get(i));
        }
        return result;
    }

    /**
     * Server giả lập để test cục bộ độc lập
     */
    private static void startMockServer(int port) {
        Thread serverThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("[MockServer] Đang lắng nghe tại cổng " + port + "...");
                Socket clientSocket = serverSocket.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                // Nhận mã SV và mã câu hỏi
                String clientHello = in.readLine();
                System.out.println("[MockServer] Nhận được đăng ký từ Client: " + clientHello);

                // Dữ liệu mẫu gửi cho Client (chuỗi số nguyên)
                // Ví dụ: 10, 22, 9, 33, 21, 50, 41, 60, 80
                // LIS là: [10, 22, 33, 50, 60, 80] (độ dài 6)
                String mockArray = "10, 22, 9, 33, 21, 50, 41, 60, 80\n";
                out.write(mockArray);
                out.flush();
                System.out.println("[MockServer] Đã gửi mảng thử nghiệm: " + mockArray.trim());

                // Đọc kết quả Client giải xong gửi lên
                String clientResult = in.readLine();
                System.out.println("[MockServer] Nhận được kết quả từ Client: " + clientResult);

                clientSocket.close();
                System.out.println("[MockServer] Đã đóng kết nối với Client.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();

        // Chờ 200ms để Server kịp bind port trước khi Client connect
        try {
            Thread.sleep(200);
        } catch (InterruptedException ignored) {}
    }

    /**
     * THAM KHẢO: Nếu đề thi yêu cầu DATA STREAM (DataInputStream / DataOutputStream):
     * 
     * Socket socket = new Socket("36.50.135.242", 2207);
     * DataInputStream dis = new DataInputStream(socket.getInputStream());
     * DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
     * 
     * // 1. Gửi mã SV và mã câu hỏi
     * dos.writeUTF("B23DCCN592;qCode");
     * dos.flush();
     * 
     * // 2. Nhận dữ liệu:
     * // TH A (server gửi chuỗi):
     * // String data = dis.readUTF();
     * // TH B (server gửi n số nguyên):
     * // int n = dis.readInt();
     * // List<Integer> numbers = new ArrayList<>();
     * // for (int i = 0; i < n; i++) numbers.add(dis.readInt());
     * 
     * // 3. Tính LIS:
     * // List<Integer> lis = findLIS(numbers);
     * 
     * // 4. Gửi kết quả:
     * // dos.writeInt(lis.size());
     * // for (int num : lis) dos.writeInt(num);
     * // dos.flush();
     * // socket.close();
     */
}
