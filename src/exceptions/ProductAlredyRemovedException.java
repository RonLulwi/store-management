package exceptions;

public class ProductAlredyRemovedException extends Exception {
	
	public ProductAlredyRemovedException() {
		super("The last product already removed");
	}

}
