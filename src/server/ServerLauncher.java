package javatalk.server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerLauncher {

	private static final int PORT = 4242;
	
	public static void main(String[] args) {
		
		try (ServerSocket listener = new ServerSocket(PORT)) {
			
			while (true) {
				new Thread(new Handler(listener.accept())).start();
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
