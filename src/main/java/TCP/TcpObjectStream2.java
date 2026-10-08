package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TcpObjectStream2 {
    public static String convert(String s){
        String lai=s.substring(1, s.length()).toLowerCase();
        Character s1=Character.toUpperCase(s.charAt(0));
        return s1+lai;
    }

    public static void main(String[] args) throws Exception{
        Socket socket=new Socket("36.50.135.242", 2209);

        ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());

        outputStream.writeObject("B23DCCN592;YSj8TRMv");
        outputStream.flush();

        Customer customer=(Customer) inputStream.readObject();

        String[] s=customer.name.split("\\s+");

        StringBuilder sb=new StringBuilder(s[s.length-1].toUpperCase()+",");
        for (int i=0;i<s.length-1;i++) {
            sb.append(" ");
            sb.append(convert(s[i]));
        }

        customer.setName(sb.toString());

        String ngay=customer.dayOfBirth;

        customer.setDayOfBirth(ngay.substring(3,5)+"/"+ngay.substring(0,2)+"/"+ngay.substring(6,10));

        StringBuilder s1=new StringBuilder();
        for (int i=0;i<s.length-1;i++) s1.append(Character.toLowerCase(s[i].charAt(0)));
        s1.append(s[s.length-1]);

        customer.setUserName(s1.toString().toLowerCase());

        outputStream.writeObject(customer);
        outputStream.flush();

        socket.close();
    }
}

