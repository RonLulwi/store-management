package exceptions;

public class ProductNotExistException extends Exception {
	
	public ProductNotExistException() {
		super("Product does not exist");
	}

}
