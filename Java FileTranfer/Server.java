import java.io.*;
import java.net.*;

class Server {
    public static void main(String args[]) throws Exception {
        ServerSocket serverSocket = new ServerSocket(6789);
		
        Socket connectionSocket = serverSocket.accept();
		
        DataInputStream dis = new DataInputStream(connectionSocket.getInputStream());

        byte[] receivedData = new byte[dis.readInt()]; 

        for(int i = 0; i < receivedData.length; i++)
            receivedData[i] = dis.readByte();

        connectionSocket.close();
        serverSocket.close();

        FileOutputStream fos = new FileOutputStream("received.jpg");
        fos.write(receivedData);
        fos.close();
    }
}