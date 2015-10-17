package microblog.kitsch.helper;

/**
 * 
 */
public class DataDuplicatedException extends Exception {
	// Variable
	private static final long serialVersionUID = 804750905920832192L;
	
	// Constructors
	public DataDuplicatedException() {
		super();
	}
	public DataDuplicatedException(String message) {
		super(message);
	}
	public DataDuplicatedException(String message, Throwable t) {
		super(message, t);
	}
	public DataDuplicatedException(Throwable t) {
		super(t);
	}
}
