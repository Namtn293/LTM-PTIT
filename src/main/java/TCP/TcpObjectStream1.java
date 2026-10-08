package TCP;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TcpObjectStream1 {
    public static void main(String[] args) throws Exception{
        Socket socket=new Socket("36.50.135.242", 2209);

        ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());

        outputStream.writeObject("B23DCCN592;B6j6ME18");
        outputStream.flush();

        Laptop laptop=(Laptop) inputStream.readObject();

        String[] s=laptop.getName().split("\\s+");

        StringBuilder sb=new StringBuilder();
        sb.append(s[s.length-1]);
        if (s.length>1){

        for (int i=1;i<s.length-1;i++) {sb.append(" ");sb.append(s[i]);}
            sb.append(" ");
        sb.append(s[0]);}

        laptop.setName(sb.toString());

        StringBuilder so=new StringBuilder(String.valueOf(laptop.getQuantity()));

        String sp1=so.reverse().toString();

        laptop.setQuantity(Integer.parseInt(sp1));

        outputStream.writeObject(laptop);
        outputStream.flush();

        socket.close();
    }
}
