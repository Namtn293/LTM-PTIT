package TCP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class TcpCharacterStream2 {

    public static void main(String[] args) throws Exception{
        Socket socket=new Socket("36.50.135.242", 2208);

        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));


        writer.write("B23DCCN592;tg7a3jT4\n");
        writer.flush();

        String[] s=reader.readLine().split("\\s+");

        Map<Character, Integer> map=new LinkedHashMap<>();

        for (String i:s){
            System.out.println(i);
            for (int j=0;j<i.length();j++){
                if (i.charAt(j)!=' '){
                    if (!map.containsKey(i.charAt(j))) map.put(i.charAt(j),1);
                    else {
                        int tg=map.get(i.charAt(j));
                        map.put(i.charAt(j),tg+1);
                    }
                }
            }
        }

        String s4="";
        for (Character j:map.keySet()){
            if (map.get(j)>1) s4+=j+":"+map.get(j)+",";
        }
        writer.write(s4);
        writer.write("\n");
        writer.flush();
    }
}
