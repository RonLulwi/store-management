package command;

import exceptions.MessageEmptyException;
import store.Store;

public class SendMessageCommand implements Command {
	
	private Store store;
	private String msg;
	
	public SendMessageCommand(Store store, String msg) {
		this.store = store;
		this.msg = msg;
	}

	@Override
	public void execute() throws MessageEmptyException {
		store.sendMessage(msg);
	}

}
