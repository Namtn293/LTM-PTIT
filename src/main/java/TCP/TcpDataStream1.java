package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TcpDataStream1 {
    public static void main(String[] args) throws Exception{
        Socket socket=new Socket("36.50.135.242", 2207);

        DataInputStream inputStream=new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream=new DataOutputStream(socket.getOutputStream());

        outputStream.writeUTF("B23DCCN592;rCoEWSPQ");
        outputStream.flush();

        int a=inputStream.readInt();
        int b=inputStream.readInt();


        int c=a+b;
        int d=a*b;
        String s=c+" "+d;

        outputStream.writeInt(c);
        outputStream.writeInt(d);

        outputStream.flush();

        socket.close();


    }
}
