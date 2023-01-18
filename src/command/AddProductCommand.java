package command;

import exceptions.CatalogNumberException;
import exceptions.OrderNotSetException;
import store.Product;
import store.Store;

public class AddProductCommand implements Command {
	
	private Store store;
	private Product product;
	private String catalogNum;
	
	public AddProductCommand(Store store, Product product, String catalogNum) {
		this.store = store;
		this.product = product;
		this.catalogNum = catalogNum;
	}

	@Override
	public void execute() throws CatalogNumberException, OrderNotSetException {
		store.addProduct(catalogNum, product);
	}

}
