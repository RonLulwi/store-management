package command;

import exceptions.NoProductsException;
import store.Store;

public class RemoveAllProductsCommand implements Command {
	
	private Store store;

	public RemoveAllProductsCommand(Store store) {
		this.store = store;
	}

	@Override
	public void execute() throws NoProductsException  {
		store.removeAllProducts();
	}

}
