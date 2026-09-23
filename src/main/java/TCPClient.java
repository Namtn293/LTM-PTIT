import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class TCPClient {
    public static void main(String[] args) {
        String serverIp="36.50.135.242";
        int portIp=2206;

        try (Socket socket=new Socket(serverIp, portIp)){
            InputStream inputStream=socket.getInputStream();
            OutputStream outputStream=socket.getOutputStream();

            String request="B23DCCN592;ueDFJMbw";
            outputStream.write(request.getBytes(StandardCharsets.UTF_8));

            byte[] bytes=new byte[1024];
            int bytesH=inputStream.read(bytes);
            String response=new String(bytes, 0, bytesH, StandardCharsets.UTF_8);

            List<Integer> list= Arrays.stream(response.split(","))
                    .map(Integer::parseInt)
                    .toList();

            int max1=0,max2=0,vt=0;
            for (int i=0;i<list.size();i++){
                if (max1<list.get(i)){
                    max1=list.get(i);
                } else if (max2<list.get(i)){
                    max2=list.get(i);
                    vt=i;
                }
            }

            String kq=max2+","+vt;
            outputStream.write(kq.getBytes(StandardCharsets.UTF_8));

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}