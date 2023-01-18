package exceptions;

public class CatalogNumberException extends Exception {
	
	public CatalogNumberException() {
		super("Catalog number can not by empty");
	}

}
