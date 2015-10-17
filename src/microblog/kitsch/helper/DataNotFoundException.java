package microblog.kitsch.helper;

/**
 * 
 */
public class DataNotFoundException extends Exception {
	// Variable
	private static final long serialVersionUID = -8760959465394114509L;
	
	// Constructors
	public DataNotFoundException() {
		super();
	}
	public DataNotFoundException(String message) {
		super(message);
	}
	public DataNotFoundException(String message, Throwable t) {
		super(message, t);
	}
	public DataNotFoundException(Throwable t) {
		super(t);
	}
}
