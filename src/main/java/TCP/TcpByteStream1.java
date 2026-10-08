package TCP;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;

public class TcpByteStream1 {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("36.50.135.242", 2206);

        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        outputStream.write("B23DCCN592;SO1MoO1U".getBytes());
        outputStream.flush();

        byte[] buffer=new byte[1024];
        int n=inputStream.read(buffer);

        String[] s=new String(buffer,0,n).split(",");

        List<Integer> set=new ArrayList<>();

        for (String s1:s){
            set.add(Integer.parseInt(s1));
        }
        set.sort(Comparator.naturalOrder());

        int min1=10000,c1=0,c2=0;
        for (int i=0;i<set.size()-1;i++){
            if (min1>(set.get(i+1)-set.get(i))){
                min1=set.get(i+1)-set.get(i);
                c1=set.get(i);c2=set.get(i+1);
            }
        }

        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(min1);
        stringBuilder.append(",");
        stringBuilder.append(c1);
        stringBuilder.append(",");
        stringBuilder.append(c2);

        outputStream.write(stringBuilder.toString().getBytes());
        outputStream.flush();
        socket.close();
    }
}
