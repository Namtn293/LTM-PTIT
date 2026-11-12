package RMI;

import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;

public class RmiLocalPeakClient {
    public static void main(String[] args) throws Exception {
        String studentCode = "B23DCCN592";
        String qCode = "3s0xpDBF";

        DataService service = (DataService) Naming.lookup("rmi://36.50.135.242/RMIDataService");

        // b. Nhận mảng số nguyên từ server
        Object raw = service.requestData(studentCode, qCode);
        if (raw == null) {
            System.err.println("Server tra ve null - kiem tra lai studentCode hoac qCode!");
            return;
        }

        List<Integer> arr = toIntList(raw);

        // c. Tìm các vị trí đỉnh cục bộ (chỉ số bắt đầu từ 1)
        List<Integer> peaks = new ArrayList<>();
        int n = arr.size();
        for (int i = 0; i < n; i++) {
            boolean leftOk = (i == 0) || (arr.get(i) > arr.get(i - 1));
            boolean rightOk = (i == n - 1) || (arr.get(i) > arr.get(i + 1));
            if (leftOk && rightOk) {
                peaks.add(i + 1);
            }
        }

        // d. Gửi List<Integer> vị trí đỉnh cục bộ trở lại server
        service.submitData(studentCode, qCode, peaks);
    }

    private static List<Integer> toIntList(Object raw) {
        List<Integer> arr = new ArrayList<>();
        if (raw instanceof List<?>) {
            for (Object item : (List<?>) raw) {
                arr.add(((Number) item).intValue());
            }
        } else if (raw instanceof int[]) {
            for (int x : (int[]) raw) {
                arr.add(x);
            }
        } else if (raw instanceof Integer[]) {
            for (Integer x : (Integer[]) raw) {
                arr.add(x);
            }
        } else if (raw instanceof String) {
            String cleaned = ((String) raw).replaceAll("[\\[\\]\\s]", "");
            if (!cleaned.isEmpty()) {
                for (String part : cleaned.split(",")) {
                    arr.add(Integer.parseInt(part));
                }
            }
        } else {
            throw new IllegalArgumentException("Kieu du lieu khong ho tro: " + raw.getClass().getName());
        }
        return arr;
    }
}

