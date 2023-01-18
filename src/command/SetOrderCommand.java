package command;

import exceptions.OrderAlreadySetException;
import store.Store;

public class SetOrderCommand implements Command {
	
	private Store store;
	private Store.Order order;
	
	public SetOrderCommand(Store store, Store.Order order) {
		this.store = store;
		this.order = order;
	}

	@Override
	public void execute() throws OrderAlreadySetException {
		store.setOrder(order);
	}

}
