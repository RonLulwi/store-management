package listeners;

public interface ModelEventsListener {

	void setOrderInModel();

	void addProductToModel(String catalogNum);

	void removeProductFromModel(String catalogNum);

	void removeAllProductsFromModel();
}
