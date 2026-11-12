package RMI;

import ws.object.Customer;
import ws.object.EmployeeY;
import ws.object.SoapObjectService;

import java.net.URI;
import java.rmi.Naming;
import java.util.*;
import java.util.stream.Collectors;

//& "C:\Program Files\Java\jdk1.8.0_202\bin\wsimport.exe" -keep -Xnocompile -s src/main/java -p ws.character http://36.50.135.242:2221/CharacterService?wsdl
//& "C:\Program Files\Java\jdk1.8.0_202\bin\wsimport.exe" -keep -Xnocompile -s src/main/java -p ws.data http://36.50.135.242:2221/DataService?wsdl
//& "C:\Program Files\Java\jdk1.8.0_202\bin\wsimport.exe" -keep -Xnocompile -s src/main/java -p ws.object http://36.50.135.242:2221/ObjectService?wsdl


//double rounded = Math.round(x * 100.0) / 100.0;
//String roundedStr = String.format("%.2f", x);

public class rmi {

    // WS DATASERVICE
    // public static void main(String[] args) {
    // String studentCode = "B23DCCN592"; // Thay bằng mã SV của bạn
    // String qCode = "2esS0yqR";
    //
    // DataService service = new DataService();
    // SoapDataService port = service.getSoapDataServicePort();
    //
    // // a. Lấy danh sách số nguyên từ server
    // List<Integer> list = port.getData(studentCode, qCode);
    //
    // // b. Ghép thành số lớn nhất
    // Integer result = list.stream()
    // .mapToInt(Integer::intValue).sum();
    //
    //
    // // c. Gửi kết quả về server
    // port.submitDataString(studentCode, qCode, result.toString());
    // }




//    WS CHARACTER SERVICE
//    public static void main(String[] args) {
//        String studentCode = "B23DCCN592";
//        String qCode = "211AJSub"; // Thay bằng qCode trên đề
//
//        CharacterService service = new CharacterService();
//        SoapCharacterService port = service.getSoapCharacterServicePort();
//
//        // a. Nhận danh sách chuỗi từ server
//        List<String> list = port.requestStringArray(studentCode, qCode);
//
//        // b. Sắp xếp các từ theo số lượng nguyên âm tăng dần, cùng số nguyên âm thì theo thứ tự từ điển
//        if (list != null) {
//            list.sort(Comparator.comparingInt(rmi::countVowels).thenComparing(Comparator.naturalOrder()));
//        }
//
//        // c. Gửi danh sách chuỗi kết quả trở lại server
//        port.submitCharacterStringArray(studentCode, qCode, list);
//    }
//
//    private static int countVowels(String s) {
//        int count = 0;
//        for (int i = 0; i < s.length(); i++) {
//            char c = Character.toLowerCase(s.charAt(i));
//            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
//                count++;
//            }
//        }
//        return count;
//    }



//    WS OBJECT SERVICE
//public static void main(String[] args) {
//    String studentCode = "B23DCCN592";
//    String qCode = "9veNWc1x"; // Thay bằng qCode trên đề
//
//    ObjectService service = new ObjectService();
//    SoapObjectService port = service.getSoapObjectServicePort();
//
//    // a. Nhận danh sách EmployeeY từ server
//    List<Customer> list = port.requestListCustomer(studentCode, qCode);
//
//    // b. Sắp xếp thâm niên từ cao đến thấp (startDate sớm nhất đứng đầu, giữ nguyên thứ tự nếu bằng nhau)
//    List<Customer> lis1=list.stream().filter(c-> c.getTotalSpent()>500 && c.getPurchaseCount()>=5).collect(Collectors.toList());
//
////    Collections.sort(list, (a,b) ->{
////        return a.getName().compareTo(b.getName());
////    });
//
//    // c. Gửi danh sách đã sắp xếp trở lại server
//    port.submitListCustomer(studentCode, qCode, lis1);
//}

//RMI BYTE
//public static void main(String[] args) throws Exception {
//    String studentCode = "B23DCCN592";
//    String qCode = "0eBsoqyP";
//
//    ByteService service = (ByteService) Naming.lookup("rmi://36.50.135.242/RMIByteService");
//
//    // a. Nhận mảng byte[] từ server
//    byte[] data = service.requestData(studentCode, qCode);
//
//    // b. Tìm phần tử xuất hiện ít nhất (nếu bằng nhau lấy phần tử xuất hiện đầu tiên)
//    Map<Byte, Integer> freq = new LinkedHashMap<>();
//    for (byte b : data) {
//        freq.put(b, freq.getOrDefault(b, 0) + 1);
//    }
//
//    byte minByte = 0;
//    int minCount = Integer.MAX_VALUE;
//    for (Map.Entry<Byte, Integer> entry : freq.entrySet()) {
//        if (entry.getValue() < minCount) {
//            minCount = entry.getValue();
//            minByte = entry.getKey();
//        }
//    }
//
//    // c. Gửi mảng byte kết quả [giá trị, số lần xuất hiện] trở lại server
//    byte[] result = new byte[]{minByte, (byte) minCount};
//    service.submitData(studentCode, qCode, result);
//}


//    RMI CHARACTER
//public static void main(String[] args) throws Exception {
//    String studentCode = "B23DCCN592";
//    String qCode = "MTa4Tnoi";
//
//    CharacterService service = (CharacterService) Naming.lookup("rmi://36.50.135.242/RMICharacterService");
//
//    // a. Nhận chuỗi ngẫu nhiên từ server
//    String s = service.requestCharacter(studentCode, qCode);
//    if (s == null) {
//        System.err.println("Server tra ve null - kiem tra lai studentCode hoac qCode!");
//        return;
//    }
//
//    // b. Mã hóa URL theo quy tắc phần đường dẫn của URI
//    String encoded = new URI(null, null, s, null).toASCIIString();
//
//    // c. Gửi chuỗi đã mã hóa trở lại server
//    service.submitCharacter(studentCode, qCode, encoded);
//}


//    RMI CHARACTER số la mã
//public static void main(String[] args) throws Exception {
//    String studentCode = "B23DCCN592";
//    String qCode = "3GmhwJpX"; // Thay bằng qCode trên đề
//
//    CharacterService service = (CharacterService) Naming.lookup("rmi://36.50.135.242/RMICharacterService");
//
//    // a. Nhận chuỗi số thập phân từ server
//    String s = service.requestCharacter(studentCode, qCode);
//    if (s == null || s.trim().isEmpty()) {
//        System.err.println("Server tra ve rong - kiem tra lai studentCode hoac qCode!");
//        return;
//    }
//
//    // b. Chuyển đổi sang chuỗi số La Mã
//    int number = Integer.parseInt(s.trim());
//    String roman = toRoman(number);
//
//    // c. Gửi kết quả trở lại server
//    service.submitCharacter(studentCode, qCode, roman);
//}
//
//    private static String toRoman(int num) {
//        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
//        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
//
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < values.length; i++) {
//            while (num >= values[i]) {
//                num -= values[i];
//                sb.append(symbols[i]);
//            }
//        }
//        return sb.toString();
//    }

//RMI DATA
//public static void main(String[] args) throws Exception {
//    String studentCode = "B23DCCN592";
//    String qCode = "3s0xpDBF";
//
//    DataService service = (DataService) Naming.lookup("rmi://36.50.135.242/RMIDataService");
//
//    // b. Nhận mảng số nguyên từ server
//    Object raw = service.requestData(studentCode, qCode);
//    if (raw == null) {
//        System.err.println("Server tra ve null - kiem tra lai studentCode hoac qCode!");
//        return;
//    }
//
//    List<Integer> arr = toIntList(raw);
//
//    // c. Tìm các vị trí đỉnh cục bộ (chỉ số bắt đầu từ 1)
//    List<Integer> peaks = new ArrayList<>();
//    int n = arr.size();
//    for (int i = 0; i < n; i++) {
//        boolean leftOk = (i == 0) || (arr.get(i) > arr.get(i - 1));
//        boolean rightOk = (i == n - 1) || (arr.get(i) > arr.get(i + 1));
//        if (leftOk && rightOk) {
//            peaks.add(i + 1);
//        }
//    }
//
//    // d. Gửi List<Integer> vị trí đỉnh cục bộ trở lại server
//    service.submitData(studentCode, qCode, peaks);
//}
//
//    private static List<Integer> toIntList(Object raw) {
//        List<Integer> arr = new ArrayList<>();
//        if (raw instanceof List<?>) {
//            for (Object item : (List<?>) raw) {
//                arr.add(((Number) item).intValue());
//            }
//        } else if (raw instanceof int[]) {
//            for (int x : (int[]) raw) {
//                arr.add(x);
//            }
//        } else if (raw instanceof Integer[]) {
//            for (Integer x : (Integer[]) raw) {
//                arr.add(x);
//            }
//        } else if (raw instanceof String) {
//            String cleaned = ((String) raw).replaceAll("[\\[\\]\\s]", "");
//            if (!cleaned.isEmpty()) {
//                for (String part : cleaned.split(",")) {
//                    arr.add(Integer.parseInt(part));
//                }
//            }
//        } else {
//            throw new IllegalArgumentException("Kieu du lieu khong ho tro: " + raw.getClass().getName());
//        }
//        return arr;
//    }

//RMI OBJECT
//public static void main(String[] args) throws Exception {
//    String studentCode = "B23DCCN592";
//    String qCode = "doL8Bv1b";
//
//    RMI.ObjectService service = (ObjectService) Naming.lookup("rmi://36.50.135.242/RMIObjectService");
//
//    // b. Nhận đối tượng RMI.TicketSla từ server
//    TicketSla ticket = (TicketSla) service.requestObject(studentCode, qCode);
//    if (ticket == null) {
//        System.err.println("Server tra ve null - kiem tra lai studentCode hoac qCode!");
//        return;
//    }
//
//    // c. Kiểm tra vi phạm SLA và gán action
//    String priority = ticket.getPriority() == null ? "" : ticket.getPriority().trim().toUpperCase();
//    int hours = ticket.getOpenedHoursAgo();
//
//    boolean breached = isBreached(priority, hours);
//    ticket.setBreached(breached);
//
//    if (!breached) {
//        ticket.setAction("MONITOR");
//    } else if ("CRITICAL".equals(priority) || hours > 96) {
//        ticket.setAction("ESCALATE_L2");
//    } else {
//        ticket.setAction("ESCALATE_L1");
//    }
//
//    // d. Gửi đối tượng đã cập nhật trở lại server
//    service.submitObject(studentCode, qCode, ticket);
//}
//
//    private static boolean isBreached(String priority, int hours) {
//        switch (priority) {
//            case "CRITICAL":
//                return hours > 2;
//            case "HIGH":
//                return hours > 8;
//            case "MEDIUM":
//                return hours > 24;
//            case "LOW":
//                return hours > 72;
//            default:
//                return false;
//        }
//    }
}
