package javatalk.client;

import java.io.IOException;
import java.io.ObjectOutputStream;

import javatalk.client.util.FileSaveLoadUtil;
import javatalk.model.Message;
import javatalk.model.TypeOfMessage;

/*
 * 메세지를 송신하는 클래스
 */
public class Sender {

	String name;
	ObjectOutputStream objectOutputStream;
	private static Sender sender;
	
	public Sender(ObjectOutputStream objectOutputStream, String name) {
		this.name = name;
		this.objectOutputStream = objectOutputStream;
		sender = this;
	}
	
	public void sendMessage(String userMessage) {
		Message message = getMessage(userMessage);
		message.setType(TypeOfMessage.MESSAGE);
		send(message);
	}
	
	public void sendWhisper(String userMessage, String whisperTarget) {
		Message message = getMessage(userMessage);
		message.setType(TypeOfMessage.WHISPER);
		message.setWhisperTarget(whisperTarget);
		send(message);
	}
	
	public void sendImage(String imagePath) {
		Message message = getMessage(null);
		message.setType(TypeOfMessage.IMAGE);
		message.setImageExtention(imagePath.substring(imagePath.length()-4, imagePath.length()));
		message.setImage(FileSaveLoadUtil.fileLoad(imagePath));
		send(message);
	}
	
	public void sendSearch(String keyword) {
		Message message = getMessage(keyword);
		message.setType(TypeOfMessage.SEARCH);
		send(message);
	}
	
	public Message getMessage(String userMessage) {
		Message message = new Message();
		message.setName(name);
		message.setMessage(userMessage);		
		return message;
	}
	
	public void send(Message message) {
		try {
			objectOutputStream.writeObject(message);
			objectOutputStream.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Sender getSender() {
		return sender;
	}
}
