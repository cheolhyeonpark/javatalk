package javatalk.client;

import javatalk.client.frame.AppFrame;

/*
 * 클라이언트 런쳐
 */
public class ClientLauncher {

	public static void main(String[] args) {
		try {
			AppFrame frame = new AppFrame();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
