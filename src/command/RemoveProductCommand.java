package command;

import exceptions.NoProductsException;
import exceptions.ProductNotExistException;
import store.Store;

public class RemoveProductCommand implements Command {
	
	private Store store;
	private String catalogNum;
	
	public RemoveProductCommand(Store store, String catalogNum) {
		this.store = store;
		this.catalogNum = catalogNum;
	}

	@Override
	public void execute() throws NoProductsException, ProductNotExistException  {
		store.removeProduct(catalogNum);
	}

}
