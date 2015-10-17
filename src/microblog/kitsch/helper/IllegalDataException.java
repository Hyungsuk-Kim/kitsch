package microblog.kitsch.helper;

public class IllegalDataException extends Exception {
	private static final long serialVersionUID = -266360421228397907L;

	public IllegalDataException () {
		super();
	}
	
	public IllegalDataException (String message) {
		super(message);
	}
	
	public IllegalDataException (Throwable t) {
		super(t);
	}
	
	public IllegalDataException (String message, Throwable t) {
		super(message, t);
	}
}
