package exceptions;

public class NameException extends Exception{
	
	public NameException() {
		super("Name cannot be empty");
	}

}
