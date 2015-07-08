import java.io.*;
import java.net.*;

class Client {
    public static void main(String args[]) throws Exception {
        long start = System.currentTimeMillis();
        Socket clientSocket = new Socket("127.0.0.1", 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        File file = new File("c:\\hot.jpg");
        FileInputStream fin = new FileInputStream(file);
        byte sendData[] = new byte[(int)file.length()];
        fin.read(sendData);

		outToServer.writeInt(sendData.length);
        outToServer.write(sendData, 0, sendData.length);
        clientSocket.close();

        long end = System.currentTimeMillis();
        System.out.println("Took " + (end - start) + "ms");
    }
}