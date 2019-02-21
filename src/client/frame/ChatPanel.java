package javatalk.client.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.text.html.HTMLDocument;

import javatalk.client.Sender;
import javatalk.client.util.FileChooserUtil;
import javatalk.client.util.FileSaveLoadUtil;
import javatalk.client.util.HTMLMaker;
import javatalk.client.util.UserList;
import javatalk.model.ChatCommand;
import javatalk.model.Message;

/*
 * 실제로 채팅이 이뤄지는 패널
 */
public class ChatPanel extends JPanel {

	JScrollPane chatScrollPane;
	JList userList;
	JTextPane chatTextPane;
	JTextArea txtrMessage;
	HTMLDocument doc;
	DefaultListModel<String> userListModel = new DefaultListModel<String>();
	private StringBuffer messageList = new StringBuffer();
	private boolean isOpenList = false;
	private StringBuffer chatLog = new StringBuffer();
	private HTMLMaker htmlMaker = new HTMLMaker();
	
	public ChatPanel() {		
		setLayout(null);
		
		JPanel chatBoardPane = new JPanel();
		chatBoardPane.setBackground(Color.PINK);
		chatBoardPane.setBounds(0, 0, 300, 440);
		add(chatBoardPane);
		chatBoardPane.setLayout(null);
		
		chatScrollPane = new JScrollPane();
		chatScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		chatScrollPane.setBounds(0, 45, 300, 395);
		chatBoardPane.add(chatScrollPane);
		
		chatTextPane = new JTextPane();
		chatTextPane.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		chatTextPane.setBackground(Color.PINK);
		chatScrollPane.setViewportView(chatTextPane);
		chatTextPane.setText("");
				
		userList = new JList(userListModel);
		userList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isDoubleClicked(e)) {
					setWhisperCommand(userList.getSelectedValue().toString());
				}
			}
		});
		userList.setBackground(Color.WHITE);
		userList.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		chatScrollPane.setColumnHeaderView(userList);
		userList.setVisible(false);
		userList.setVisibleRowCount(0);
		userList.setAutoscrolls(true);
		
		JLabel lblUserList = new JLabel("≡");
		lblUserList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				userListControl();
			}
		});
		lblUserList.setFont(new Font("맑은 고딕", Font.BOLD, 36));
		lblUserList.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserList.setBounds(12, 0, 40, 40);
		chatBoardPane.add(lblUserList);
		chatTextPane.setContentType("text/html");
		doc = (HTMLDocument) chatTextPane.getStyledDocument();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 450, 189, 70);
		add(scrollPane);
		
		txtrMessage = new JTextArea();
		txtrMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (isEnter(e)) {
					pressEnter(txtrMessage.getText().replaceAll("\n", ""));
				}
			}
		});
		txtrMessage.setLineWrap(true);
		txtrMessage.setWrapStyleWord(true);
		scrollPane.setViewportView(txtrMessage);
		
		JButton btnNewButton = new JButton("전송");
		btnNewButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		btnNewButton.setBackground(Color.ORANGE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pressEnter(txtrMessage.getText());
			}
		});
		btnNewButton.setBounds(211, 450, 65, 35);
		add(btnNewButton);
		
		JLabel lblImage = new JLabel(new ImageIcon("images/image.png"));
		lblImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				sendImage();
			}
		});
		lblImage.setBounds(211, 490, 30, 30);
		add(lblImage);
		
		JLabel lblSave = new JLabel(new ImageIcon("images/save.png"));
		lblSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				saveChatLog();
			}
		});
		lblSave.setBounds(246, 490, 30, 30);
		add(lblSave);		
	}
	
	private void setWhisperCommand(String whisperTarget) {
		txtrMessage.setText(ChatCommand.WHISPER+" "+whisperTarget+" ");
	}

	private boolean isDoubleClicked(MouseEvent e) {
		return e.getClickCount() == 2;
	}
	
	private void userListControl() {
		if (isOpenList) {
			userListClose();
		} else {
			userListOpen();
		}
	}
	
	private void userListOpen() {
		setUserList();
		userList.setVisible(true);
		userList.setVisibleRowCount(8);
		isOpenList = true;
	}
	
	private void setUserList() {
		userListModel.clear();
		for (String userName : UserList.getUsernameList()) {
			userListModel.addElement(userName); 
		}
	}

	private void userListClose() {
		userList.setVisible(false);
		userList.setVisibleRowCount(0);
		isOpenList = false;
	}
	
	private boolean isEnter(KeyEvent e) {
		return e.getKeyCode() == KeyEvent.VK_ENTER;
	}

	private void pressEnter(String userMessage) {
		if (isNullString(userMessage)) {
			return;
		} else if (isWhisper(userMessage)) {
			sendWhisper(userMessage);
		} else if (isSearch(userMessage)) {
			sendSearch(userMessage);
		} else {
			sendMessage(userMessage);
		}
		txtrMessage.setText("");
		txtrMessage.setCaretPosition(0);
	}
	
	private void sendWhisper(String userMessage) {
		String whisperTarget = userMessage.split(" ", 3)[1];
		String sendingMessage = userMessage.replaceAll(ChatCommand.WHISPER+" "+whisperTarget, "");
		Sender.getSender().sendWhisper(sendingMessage, whisperTarget);
	}

	private void sendSearch(String userMessage) {
		Sender.getSender().sendSearch(userMessage);
	}

	private void sendMessage(String userMessage) {
		Sender.getSender().sendMessage(userMessage);
	}

	private boolean isNullString(String userMessage) {
		return userMessage == null || userMessage.equals("");
	}

	private boolean isWhisper(String text) {
		return text.startsWith(ChatCommand.WHISPER.toString());
	}
	
	private boolean isSearch(String userMessage) {
		return userMessage.startsWith(ChatCommand.SEARCH.toString());
	}

	private void sendImage() {
		String imagePath = FileChooserUtil.getFilePath();
		if (imagePath == null) {
			return;
		} else if (imagePath.endsWith("png") || imagePath.endsWith("jpg")) {
			Sender.getSender().sendImage(imagePath);
		} else {
			JOptionPane.showMessageDialog(null, ".png, .jpg 확장자 파일만 전송 가능합니다.");
		}
	}
	
	private void saveChatLog() {
		String savePath = FileChooserUtil.getFilePath();
		if (savePath == null) {
			return;
		}
		FileSaveLoadUtil.fileSave(".txt", savePath+"/chatlog", chatLog.toString().getBytes());
		JOptionPane.showMessageDialog(null, "저장 완료");
	}
	
	public void addMessage(String adminMessage) {
		messageList.append(htmlMaker.getHTML(adminMessage));
		rewriteChatPane();
		addChatLog(adminMessage);
	}
	
	public void addMessage(boolean isMine, Message message) {
		messageList.append(htmlMaker.getHTML(isMine, message));
		rewriteChatPane();
		addChatLog(message.getName(), message.getMessage());
	}
	
	private void rewriteChatPane() {
		chatTextPane.setText(messageList.toString());
		chatTextPane.setCaretPosition(doc.getLength());
	}
	
	private void addChatLog(String adminMessage) {
		chatLog.append(adminMessage + "\r\n");
	}
	
	private void addChatLog(String userName, String userMsg) {
		chatLog.append(userName + " : " + userMsg + "\r\n");
	}
}
