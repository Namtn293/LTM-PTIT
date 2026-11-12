package RMI;

import java.net.URI;
import java.rmi.Naming;

public class RmiCharacterClient {
    public static void main(String[] args) throws Exception {
        String studentCode = "B23DCCN592";
        String qCode = "MTa4Tnoi";

        CharacterService service = (CharacterService) Naming.lookup("rmi://36.50.135.242/RMICharacterService");

        // a. Nhận chuỗi ngẫu nhiên từ server
        String s = service.requestCharacter(studentCode, qCode);
        if (s == null) {
            System.err.println("Server tra ve null - kiem tra lai studentCode hoac qCode!");
            return;
        }

        // b. Mã hóa URL theo quy tắc phần đường dẫn của URI
        String encoded = new URI(null, null, s, null).toASCIIString();

        // c. Gửi chuỗi đã mã hóa trở lại server
        service.submitCharacter(studentCode, qCode, encoded);
    }
}

