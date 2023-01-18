package command;

import java.util.Map.Entry;
import exceptions.NoProductsException;
import store.Product;
import store.Store;

public class ShowAllProductsCommand implements Command {
	
	private Store store;
	private String[] products;
	
	public ShowAllProductsCommand(Store store) {
		this.store = store;
		products = new String[store.getNumOfProducts()];
	}

	@Override
	public void execute() throws NoProductsException {
		int i = 0;
		for (Entry<String, Product> entry : store.getAllProducts().entrySet()) {
			products[i] = String.format("Catalog Number: %s\n %s", entry.getKey(), entry.getValue().toString());
			i++;
		}
	}
	
	public String[] getAllProducts() {
		return products;
	}
	
}
