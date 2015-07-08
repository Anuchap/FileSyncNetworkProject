import java.io.*;
import java.net.*;

public class Client {

	private static Socket mySock;

	public static void main(String[] args) {
		
		try {
			mySock = new Socket("localhost", 6789);
			
			final BufferedReader in = new BufferedReader(new InputStreamReader(mySock.getInputStream()));
			final PrintWriter out = new PrintWriter(mySock.getOutputStream(), true);
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						try {
							String message;
							
							if((message = in.readLine()) == null) {
								System.out.println(mySock.getRemoteSocketAddress() + " is Closed!!");
								break;
							} 
							
							System.out.println(message);
							
							out.println("Success!!");
						} catch (SocketException e) {
							System.out.println(mySock.getRemoteSocketAddress() + " is Closed!!");
							break;
						} catch (IOException e) { 
							e.printStackTrace(); 
						}
					}
					
					try { in.close(); } catch (IOException e) {	e.printStackTrace(); }
				}
			}).start();
		} catch (UnknownHostException e) { 
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}
	}
}
