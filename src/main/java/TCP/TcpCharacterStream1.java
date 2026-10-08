package TCP;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class TcpCharacterStream1 {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2208);

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        writer.write("B23DCCN592;QAGpG76T\n");
        writer.flush();

        String[] s=reader.readLine().split(", ");

        List<String> s2=new ArrayList<>();

        for (String s1:s){
            if(s1.endsWith(".edu")) s2.add(s1);
        }

        writer.write(String.join(", ",s2));
        writer.write("\n");
        writer.flush();

        socket.close();
    }
}