import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TCPCharacterStream {
    public static void main(String[] args) {
        String serverIp="36.50.135.242";
        int portIp=2208;
        try(Socket socket=new Socket(serverIp, portIp)){
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String maSv="B23DCCN592;tg7a3jT4";
            writer.write(maSv);
            writer.newLine();
            writer.flush();

            String response=reader.readLine();
            Map<Character, Long> map=new LinkedHashMap<>();
            for (int i=0;i<response.length();i++)
            if (' '!=response.charAt(i)) {
                if (map.containsKey(response.charAt(i))){
                    Long tg=map.get(response.charAt(i));
                    map.put(response.charAt(i), tg+1);
                } else map.put(response.charAt(i), 1L);
            }

            StringBuilder sb=new StringBuilder();
            map.forEach((c,d)->{
                if (d>1L){
                sb.append(c);sb.append(":");sb.append(d.toString());sb.append(",");}
            });

            writer.write(sb.toString());
            writer.newLine();
            writer.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
