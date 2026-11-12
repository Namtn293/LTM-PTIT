package RMI;

import java.rmi.Naming;
import java.util.Arrays;
import java.util.Locale;

public class RmiStatsClient {
    public static void main(String[] args) throws Exception {
        String studentCode = "B23DCCN592";
        String qCode = "gbbJqjpd";

        DataService service = (DataService) Naming.lookup("rmi://36.50.135.242/RMIDataService");

        // b. Nhận chuỗi CSV các số thực từ server
        Object raw = service.requestData(studentCode, qCode);
        if (raw == null) {
            System.err.println("Server tra ve null - kiem tra lai studentCode hoac qCode!");
            return;
        }

        String csv = raw.toString().trim();
        String[] parts = csv.split(",");
        int n = parts.length;
        double[] arr = new double[n];
        double sum = 0;
        for (int i = 0; i < n; i++) {
            arr[i] = Double.parseDouble(parts[i].trim());
            sum += arr[i];
        }

        // c. Tính trung bình, độ lệch chuẩn (toàn bộ dãy) và phân vị 95
        double avg = sum / n;
        double sumSq = 0;
        for (double x : arr) {
            double diff = x - avg;
            sumSq += diff * diff;
        }
        double stddev = Math.sqrt(sumSq / n);

        Arrays.sort(arr);
        int idx = (int) Math.ceil(n * 0.95) - 1;
        double p95 = arr[idx];

        // d. Gửi chuỗi kết quả theo đúng định dạng về server
        String result = String.format(Locale.US, "average=%.2f;stddev=%.2f;p95=%.2f", avg, stddev, p95);
        service.submitData(studentCode, qCode, result);
    }
}

