package exceptions;

public class PhoneException extends Exception {
	
	public PhoneException() {
		super("Phone number must be in 05XXXXXXXX format");
	}
}
