import java.io.*;
import java.net.*;
import java.util.HashMap;


public class Server {

	private static ServerSocket server;
	private static HashMap<SocketAddress, MyClient> clients = new HashMap<SocketAddress, MyClient>();

	public static void main(String[] args) {
	
		try {
			server = new ServerSocket(6789);
			
			while(true) {				
				MyClient myClient = new MyClient(server.accept());
				
				clients.put(myClient.getAddress(), myClient);
				
				System.out.println(myClient.getAddress() + " : Connected!!!");
				
				myClient.send("Welcome to Server");
		
				new Thread(myClient).start();
			}
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
	}
}

class MyClient implements Runnable {
	
	private Socket mySock;
	private BufferedReader in;
	private PrintWriter out;
	
	public MyClient(Socket mySock) {
		this.mySock = mySock;
		
		try {
			in = new BufferedReader(new InputStreamReader(mySock.getInputStream()));
			out = new PrintWriter(mySock.getOutputStream(), true);
		} catch (IOException e) {e.printStackTrace(); }
	}

	@Override
	public void run() {
		while(true) {
			try {
				String message;
				
				if((message = in.readLine()) == null) {
					System.out.println(mySock.getRemoteSocketAddress() + " : Disconnected!!!");
					break;
				} 
				
				System.out.println(mySock.getRemoteSocketAddress() + " : " + message);
				
			} catch (SocketException e) {
				System.out.println(mySock.getRemoteSocketAddress() + " : Disconnected!!!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try { in.close(); } catch (IOException e) {	e.printStackTrace(); }
	}
	
	public void send(String message) {
		out.println(message);
	}
	
	public SocketAddress getAddress() {
		return mySock.getRemoteSocketAddress();
	}
}
