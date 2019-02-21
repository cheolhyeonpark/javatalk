package javatalk.model;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private int profileNum;
	transient private ObjectOutputStream objectOutputStream;
	
	public User(String name, int profileNum, ObjectOutputStream objectOutputStream) {
		this.name = name;
		this.profileNum = profileNum;
		this.objectOutputStream = objectOutputStream;
	}
	
	public int getProfileNum() {
		return profileNum;
	}

	public void setProfileNum(int profileNum) {
		this.profileNum = profileNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}
}
