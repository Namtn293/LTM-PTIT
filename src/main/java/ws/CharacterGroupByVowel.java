package ws;

import ptit.dblab.judge.network.server.soap.CharacterService;
import ptit.dblab.judge.network.server.soap.SoapCharacterService;

import java.util.Comparator;
import java.util.List;

public class CharacterGroupByVowel {
    public static void main(String[] args) {
        String studentCode = "B23DCCN592";
        String qCode = "THAY_MA_CAU_HOI"; // Thay bằng qCode trên đề

        CharacterService service = new CharacterService();
        SoapCharacterService port = service.getSoapCharacterServicePort();

        // a. Nhận danh sách chuỗi từ server
        List<String> list = port.requestStringArray(studentCode, qCode);

        // b. Sắp xếp các từ theo số lượng nguyên âm tăng dần, cùng số nguyên âm thì theo thứ tự từ điển
        if (list != null) {
            list.sort(Comparator.comparingInt(CharacterGroupByVowel::countVowels).thenComparing(Comparator.naturalOrder()));
        }

        // c. Gửi danh sách chuỗi kết quả trở lại server
        port.submitCharacterStringArray(studentCode, qCode, list);
    }

    private static int countVowels(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = Character.toLowerCase(s.charAt(i));
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                count++;
            }
        }
        return count;
    }
}

