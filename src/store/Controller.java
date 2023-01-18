package store;

import java.util.Vector;
import command.AddProductCommand;
import command.FindProductCommand;
import command.GetAllApprovedCustomersCommand;
import command.RemoveAllProductsCommand;
import command.RemoveProductCommand;
import command.SendMessageCommand;
import command.SetOrderCommand;
import command.ShowAllProductsCommand;
import command.ShowProfitsCommand;
import command.ShowTotalProfitCommand;
import command.UndoAddProductCommand;
import exceptions.CatalogNumberException;
import exceptions.OrderAlreadySetException;
import exceptions.OrderNotSetException;
import exceptions.MessageEmptyException;
import exceptions.NameException;
import exceptions.NoApprovedCustomersException;
import exceptions.NoProductsException;
import exceptions.PhoneException;
import exceptions.PriceException;
import exceptions.ProductAlredyRemovedException;
import exceptions.ProductNotExistException;
import javafx.application.Platform;
import listeners.ModelEventsListener;
import listeners.UIEventsListener;
import view.AbstractView;

public class Controller implements UIEventsListener, ModelEventsListener {

	public final int TIME = 2000;
	private boolean threading = false;

	private Store model;
	private AbstractView view;

	private Store.Memento memento;

	public Controller(Store model, AbstractView view) {
		this.model = model;
		this.view = view;
		this.model.attachListener(this);
		this.view.attachListener(this);
		memento = null;
	}

	@Override
	public void selectedOrderInUI(String order) {
		Store.Order myOrder = null;

		switch (order) {
		case "Ascending Order":
			myOrder = Store.Order.ASCEND;
			break;
		case "Descending Order":
			myOrder = Store.Order.DESCEND;
			break;
		case "Inserting Order":
			myOrder = Store.Order.INSERT;
			break;
		}

		SetOrderCommand cmd = new SetOrderCommand(model, myOrder);
		try {
			cmd.execute();
		} catch (OrderAlreadySetException e) {
			view.showErrorMessage(e.getMessage());
		}
	}

	@Override
	public void setOrderInModel() {
		view.orderSetMessage();
	}

	@Override
	public void addProductToUI(String catalogNum, String productName, int cost, int price, String customerName,
			String phone, boolean update) {
		try {
			Product product = new Product(productName, cost, price, new Customer(customerName, phone, update));
			AddProductCommand cmd = new AddProductCommand(model, product, catalogNum);
			cmd.execute();
		} catch (CatalogNumberException | NameException | PriceException | PhoneException | OrderNotSetException e) {
			view.showErrorMessage(e.getMessage());
		}
	}

	@Override
	public void undoAddProductInUI() {
		memento = model.createMemento();
		try {
			UndoAddProductCommand cmd = new UndoAddProductCommand(model, memento);
			cmd.execute();
		} catch (ProductAlredyRemovedException | NoProductsException e) {
			view.showErrorMessage(e.getMessage());
		}
	}

	@Override
	public void addProductToModel(String catalogNum) {
		view.addProductToUI(catalogNum);
	}

	@Override
	public void findProductInUI(String catalogNum) {
		try {
			FindProductCommand cmd = new FindProductCommand(model, catalogNum);
			cmd.execute();
			view.foundProductMessage(cmd.getProduct().toString());
		} catch (ProductNotExistException | NoProductsException e) {
			view.showErrorMessage(e.getMessage());
		}
	}

	@Override
	public void showAllProductsInUI() {
		try {
			ShowAllProductsCommand cmd = new ShowAllProductsCommand(model);
			cmd.execute();
			view.showAllProductsInUI(cmd.getAllProducts());
		} catch (NoProductsException e) {
			view.showErrorMessage(e.getMessage());
		}
	}

	@Override
	public void showProfitsInUI() {
		try {
			ShowProfitsCommand cmd = new ShowProfitsCommand(model);
			cmd.execute();
			view.showProfitsInUI(cmd.getProfits());
		} catch (NoProductsException e) {
			view.showErrorMessage(e.getMessage());
		}
	}

	@Override
	public void showTotalProfitInUI() {
		ShowTotalProfitCommand cmd = new ShowTotalProfitCommand(model);
		cmd.execute();
		view.showTotalProfitInUI(cmd.getProfit());
	}

	@Override
	public void removeProductInUI(String catalogNum) {
		try {
			RemoveProductCommand cmd = new RemoveProductCommand(model, catalogNum);
			cmd.execute();
		} catch (NoProductsException | ProductNotExistException e) {
			view.showErrorMessage(e.getMessage());
		}
	}

	@Override
	public void removeProductFromModel(String catalogNum) {
		view.removedProductMessage(catalogNum);
	}

	@Override
	public void removeAllProductsInUI() {
		try {
			RemoveAllProductsCommand cmd = new RemoveAllProductsCommand(model);
			cmd.execute();
		} catch (NoProductsException e) {
			view.showErrorMessage(e.getMessage());
		}
	}

	@Override
	public void removeAllProductsFromModel() {
		view.removeAllProductsMessage();
	}

	@Override
	public void sendMessageInUI(String msg) {
		try {
			SendMessageCommand cmd = new SendMessageCommand(model, msg);
			cmd.execute();
			view.messageArrived();
		} catch (MessageEmptyException e) {
			view.showErrorMessage(e.getMessage());
		}
	}

	@Override
	public void showCustomersApprovedInUI() {
		if (!threading) {
			try {
				GetAllApprovedCustomersCommand cmd = new GetAllApprovedCustomersCommand(model);
				cmd.execute();
				Vector<String> names = cmd.getApprovedCustomers();

				threading = true;
				Thread thread = new Thread(() -> {
					try {
						for (String name : names) {
							Platform.runLater(() -> {
								view.showApprovedCustomer(name);
							});
							Thread.sleep(TIME);
						}
						Platform.runLater(() -> {
							view.finishShowApprovedCustomers();
						});
						threading = false;

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				});
				thread.start();

			} catch (NoApprovedCustomersException e) {
				view.showErrorMessage(e.getMessage());
			}
		}
	}
}
