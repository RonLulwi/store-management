package view;

import listeners.UIEventsListener;

public interface AbstractView {
	
	void attachListener(UIEventsListener listener);

	void orderSetMessage();

	void showErrorMessage(String msg);

	void addProductToUI(String catalogNum);

	void foundProductMessage(String product);
	
	void showAllProductsInUI(String[] products);

	void removedProductMessage(String catalogNum);

	void showProfitsInUI(String[] profits);

	void showTotalProfitInUI(int profit);

	void messageArrived();

	void showApprovedCustomer(String name);

	void finishShowApprovedCustomers();

	void removeAllProductsMessage();
	
}
