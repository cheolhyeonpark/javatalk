package javatalk.client.frame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AppFrame extends JFrame {

	private JPanel contentPane;
	private LoginPanel loginPane;
	private ChatPanel chatPane;

	public AppFrame() {
		setTitle("javatalk");
		loginPane = new LoginPanel(this);
		chatPane = new ChatPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 305, 580);
		changeToLogin();
	}
	
	public void changeToLogin() {		
		contentPane = loginPane;
		paintPane();
	}

	public void changeToChat() {
		contentPane = chatPane;
		paintPane();
	}
	
	public void changeToError(String errorMessage) {
		contentPane = new ErrorPanel(this, errorMessage);
		paintPane();
	}	
	
	public void paintPane() {
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		revalidate();
		repaint();
	}
	
	public LoginPanel getLoginPane() {
		return loginPane;
	}
	
	public ChatPanel getChatPane() {
		return chatPane;
	}
}