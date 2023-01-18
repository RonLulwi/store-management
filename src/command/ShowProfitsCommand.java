package command;

import java.util.Collection;

import exceptions.NoProductsException;
import store.Product;
import store.Store;

public class ShowProfitsCommand implements Command {
	
	private Store store;
	private String[] profits;
	
	public ShowProfitsCommand(Store store) {
		this.store = store;
		profits = new String[store.getNumOfProducts()];
	}

	@Override
	public void execute() throws NoProductsException  {
		Collection<Product> products = store.getAllProducts().values();
		int i = 0;
		for (Product product : products) {
			profits[i] = product.getName() + ": " + product.getProfit();
			i++;
		}
	}
	
	public String[] getProfits() {
		return profits;
	}

}
