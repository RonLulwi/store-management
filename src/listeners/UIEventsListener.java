package listeners;

public interface UIEventsListener {
	
	void selectedOrderInUI(String order);

	void addProductToUI(String catalogNum, String productName, int cost, int price, String customerName, String phone, boolean isUpdate);

	void findProductInUI(String catalogNum);

	void showAllProductsInUI();

	void undoAddProductInUI();

	void showProfitsInUI();

	void showTotalProfitInUI();

	void sendMessageInUI(String msg);

	void showCustomersApprovedInUI();

	void removeProductInUI(String catalogNum);

	void removeAllProductsInUI();
}
