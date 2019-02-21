package javatalk.client.util;

import java.util.HashMap;

import javatalk.model.FilePath;
import javatalk.model.Message;
import javatalk.model.TypeOfMessage;

/*
 * 채팅 패널에 출력되는 HTML 태그를 작성합니다.
 * getHTML(String adminMessage) - 입장, 퇴장 메세지 출력
 * getHTML(boolean isMine, Message message) - 일반 메세지 / 이미지 출력
 */
public class HTMLMaker {

	private HashMap<TypeOfMessage, String> userMsg = new HashMap<TypeOfMessage, String>();
	private HashMap<Boolean, String> align = new HashMap<Boolean, String>();
	private HashMap<Boolean, String> profile = new HashMap<Boolean, String>();
	private HashMap<Boolean, String> background = new HashMap<Boolean, String>();
	
	public HTMLMaker() {
		align.put(true, "right");
		align.put(false, "left");
		background.put(true, "yellow");
		background.put(false, "white");
	}
	
	public String getHTML(String adminMessage) {
		String htmlResult = "<div style=\"text-align:center;padding: 3px; margin: 3px;\">"
				+ "				<span style=\" color: white; font-size:12; font-family: 'Nanum Gothic';\">"
				+					adminMessage
				+ "				</span></div>";
		return htmlResult;
	}
	
	public String getHTML(boolean isMine, Message message) {
		TypeOfMessage messageType = message.getType();
		String userName = message.getName();
		String userMessage = message.getMessage();
		int profileNum = UserList.getUser(userName).getProfileNum();
		setProfile(isMine, userName, profileNum);
		setUserMsg(messageType, userMessage);
		userMessage = getMsgBox(userMessage);
		
		String htmlResult =
				"<div style=\"text-align:" + align.get(isMine) + ";\">"+
				"	<table style=\"font-size:12; font-family: 'Nanum Gothic';\">" + 
				"   	<tr>" + 
							profile.get(isMine) +
				"    	</tr>" +
				"    	<tr>" + 
				"      		<td style=\"text-align:left;\">" +
				"				<div style=\"background-color: " + background.get(isMine) + "; padding: 5px;\">" +
									userMsg.getOrDefault(messageType, userMessage) +
				"				</div>" +
				"			</td>" + 
				"    	</tr>" + 
				"	</table>" +
				"</div>";
		
		return htmlResult;
	}

	private void setProfile(boolean isMine, String userName, int profileNum) {
		String right = 		
				"	    	<th style=\"text-align:" + align.get(isMine) + ";\">" +
								userName +
				"			</th>" + 
				"   	    <th rowspan=\"2\">" +
				"				<img src=\"file:"+FilePath.PROFILEPATH.toString()+"profile"+profileNum+".png"+"\" height=\"40\" width=\"40\">" +
				"			</th>";		
		profile.put(true, right);
		
		String left = 	
				"   	    <th rowspan=\"2\">" +
				"				<img src=\"file:"+FilePath.PROFILEPATH+"profile"+profileNum+".png"+"\" height=\"40\" width=\"40\">" +
				"			</th>" +
				"	    	<th style=\"text-align:" + align.get(isMine) + ";\">" +
								userName +
				"			</th>";
		profile.put(false, left);
	}
	
	private void setUserMsg(TypeOfMessage messageType, String userMessage) {
		String imgTag = "<img src=\"file:"+FilePath.DOWNLOADFILEPATH+userMessage+"\" height=\"160\" width=\"160\">";
		userMsg.put(TypeOfMessage.IMAGE, imgTag);
	}
	
	private String getMsgBox(String userMessage) {
		String ret = "";		
		for (int i = 0; i < userMessage.length(); i++) {
			ret += userMessage.charAt(i);
			if (i != 0 && i % 15 == 0) {
				ret += "<br>";
			}
		}		
		return ret;
	}
}
