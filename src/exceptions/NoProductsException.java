package exceptions;

public class NoProductsException extends Exception {
	
	public NoProductsException() {
		super("There are no products in the system");
	}

}
