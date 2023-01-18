package command;

import java.util.Vector;

import exceptions.NoApprovedCustomersException;
import store.Store;

public class GetAllApprovedCustomersCommand implements Command {
	
	private Store store;
	private Vector<String> names;
	
	public GetAllApprovedCustomersCommand(Store store) {
		this.store = store;
	}

	@Override
	public void execute() throws NoApprovedCustomersException  {
		names = store.getResponses();
	}
	
	public Vector<String> getApprovedCustomers(){
		return names;
	}
	
}
