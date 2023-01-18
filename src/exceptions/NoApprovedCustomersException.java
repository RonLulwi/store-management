package exceptions;

public class NoApprovedCustomersException extends Exception {
	
	public NoApprovedCustomersException() {
		super("No responses have yet been received");
	}

}
