package command;

import exceptions.NoProductsException;
import exceptions.ProductAlredyRemovedException;
import store.Store;
import store.Store.Memento;

public class UndoAddProductCommand implements Command {
	
	private Store store;
	private Store.Memento memento;
	
	public UndoAddProductCommand(Store store, Memento memento) {
		this.store = store;
		this.memento = memento;
	}

	@Override
	public void execute() throws ProductAlredyRemovedException, NoProductsException {
		store.setMemento(memento);
	}

}
