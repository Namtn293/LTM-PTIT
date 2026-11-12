package RMI;

import java.rmi.Naming;

public class RmiRomanClient {
    public static void main(String[] args) throws Exception {
        String studentCode = "B23DCCN592";
        String qCode = "3GmhwJpX"; // Thay bằng qCode trên đề

        CharacterService service = (CharacterService) Naming.lookup("rmi://36.50.135.242/RMICharacterService");

        // a. Nhận chuỗi số thập phân từ server
        String s = service.requestCharacter(studentCode, qCode);
        if (s == null || s.trim().isEmpty()) {
            System.err.println("Server tra ve rong - kiem tra lai studentCode hoac qCode!");
            return;
        }

        // b. Chuyển đổi sang chuỗi số La Mã
        int number = Integer.parseInt(s.trim());
        String roman = toRoman(number);

        // c. Gửi kết quả trở lại server
        service.submitCharacter(studentCode, qCode, roman);
    }

    private static String toRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num -= values[i];
                sb.append(symbols[i]);
            }
        }
        return sb.toString();
    }
}

