package exceptions;

public class OrderAlreadySetException extends Exception {
	
	public OrderAlreadySetException() {
		super("Products order cannot be changed after initial initialization");
	}

}
