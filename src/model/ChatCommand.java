package javatalk.model;

public enum ChatCommand {
	WHISPER("/w"), SEARCH("#");
	
	private final String command;
	
	private ChatCommand(final String command) {
		this.command = command;
	}
	
	public String toString() {
		return command;
	}
}
