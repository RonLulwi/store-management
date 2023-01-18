package command;

import exceptions.NoProductsException;
import exceptions.ProductNotExistException;
import store.Product;
import store.Store;

public class FindProductCommand implements Command {
	
	private Store store;
	private String catalogNum;
	private Product product;

	public FindProductCommand(Store store, String catalogNum) {
		this.store = store;
		this.catalogNum = catalogNum;
		product = null;
	}
	
	@Override
	public void execute() throws ProductNotExistException, NoProductsException {
		product = store.getProduct(catalogNum);
	}
	
	public Product getProduct() {
		return product;
	}
	
}
