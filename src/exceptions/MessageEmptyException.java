package exceptions;

public class MessageEmptyException extends Exception {
	
	public MessageEmptyException() {
		super("Message cannot be empty");
	}

}
