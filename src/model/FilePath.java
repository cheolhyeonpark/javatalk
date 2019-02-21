package javatalk.model;

public enum FilePath {

	PROFILEPATH("images/profile/"),
	DOWNLOADFILEPATH("download/");
	
	private final String path;
	
	private FilePath(final String path) {
		this.path = path;
	}
	
	public String toString() {
		return path;
	}
}
