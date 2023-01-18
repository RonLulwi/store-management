package exceptions;

public class OrderNotSetException extends Exception {
	
	public OrderNotSetException() {
		super("The order of the products must be initialized");
	}

}
