package exceptions;

public class PriceException extends Exception {
	
	public PriceException() {
		super("Price must be a non negative number");
	}
}
