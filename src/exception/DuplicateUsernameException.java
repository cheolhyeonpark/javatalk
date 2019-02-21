package javatalk.server.exception;

public class DuplicateUsernameException extends Exception {

	private static final long serialVersionUID = 1L;

	public DuplicateUsernameException(String message) {
		super(message);
	}
}
