package view;

import java.util.Vector;
import javax.swing.JOptionPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import listeners.UIEventsListener;

public class View implements AbstractView {

	private Vector<UIEventsListener> listeners = new Vector<UIEventsListener>();

	private BorderPane bpBackGround;
	private StackPane spLayout;
	private VBox vbOptions;
	private GridPane gpSetOrder;
	private GridPane gpAddProduct;
	private GridPane gpFindProduct;
	private Label lblProduct;
	private HBox hbAllProducts;
	private HBox hbProfits;
	private Label lblTotalProfit;
	private HBox hbSendMessage;
	private VBox vbCustomers;
	private HBox hbRemoveProduct;

	public View(Stage stage) {
		setOptions();
		setAddProduct();
		setOrder();
		setFindProduct();
		setAllProducts();
		setRemoveProduct();
		setProfits();
		setTotalProfit();
		setSendMessage();
		setCustomers();
		setLayout();
		setBackGround();
		setStage(stage);
	}

	@Override
	public void attachListener(UIEventsListener listener) {
		listeners.add(listener);
	}

	private void setStage(Stage stage) {
		stage.setTitle("Store");
		stage.setScene(new Scene(bpBackGround, 1200, 650));
		stage.show();
	}

	private void setBackGround() {
		bpBackGround = new BorderPane();
		bpBackGround.setLeft(vbOptions);
		bpBackGround.setCenter(spLayout);
	}

	private void setLayout() {
		spLayout = new StackPane();
		spLayout.getChildren().addAll(gpAddProduct, gpSetOrder, gpFindProduct, hbAllProducts, hbProfits, lblTotalProfit, hbSendMessage, vbCustomers, hbRemoveProduct);
	}

	private void setOptions() {
		vbOptions = setVBox();

		Button btSetOrder = new Button("Set Order");
		Button btAddProduct = new Button("Add Product");
		Button btFindProduct = new Button("Find Product");
		Button btAllProducts = new Button("All Products");
		Button btRemove = new Button("Delete Product");
		Button btRemoveAll = new Button("Delete All Products");
		Button btProfit = new Button("Products Profit");
		Button btTotalProfit = new Button("Total Profit");
		Button btSendMessage = new Button("Send Message");
		Button btCustomers = new Button("Customers Approved");

		btSetOrder.setOnAction(e -> {
			setAllNotVisible();
			gpSetOrder.setVisible(true);
		});
		
		btAddProduct.setOnAction(e -> {
			setAllNotVisible();
			gpAddProduct.setVisible(true);
		});

		btFindProduct.setOnAction(e -> {
			setAllNotVisible();
			gpFindProduct.setVisible(true);
		});

		btAllProducts.setOnAction(e -> {
			setAllNotVisible();
			hbAllProducts.setVisible(true);
			for (UIEventsListener listener : listeners) {
				listener.showAllProductsInUI();
			}
		});
		
		btRemove.setOnAction(e -> {
			setAllNotVisible();
			hbRemoveProduct.setVisible(true);
		});
		
		btRemoveAll.setOnAction(e -> {
			setAllNotVisible();
			for (UIEventsListener listener : listeners) {
				listener.removeAllProductsInUI();
			}
		});

		btProfit.setOnAction(e -> {
			setAllNotVisible();
			hbProfits.setVisible(true);
			for (UIEventsListener listener : listeners) {
				listener.showProfitsInUI();
			}
		});

		btTotalProfit.setOnAction(e -> {
			setAllNotVisible();
			lblTotalProfit.setVisible(true);
			for (UIEventsListener listener : listeners) {
				listener.showTotalProfitInUI();
			}
		});

		btSendMessage.setOnAction(e -> {
			setAllNotVisible();
			hbSendMessage.setVisible(true);
		});

		btCustomers.setOnAction(e -> {
			setAllNotVisible();
			vbCustomers.setVisible(true);
			for (UIEventsListener listener : listeners) {
				listener.showCustomersApprovedInUI();
			}
		});

		vbOptions.getChildren().addAll(btSetOrder, btAddProduct, btFindProduct, btAllProducts, btRemove, btRemoveAll, btProfit, btTotalProfit, btSendMessage, btCustomers);
		vbOptions.setAlignment(Pos.CENTER_LEFT);
	}

	private void setOrder() {
		gpSetOrder = setGridPane();

		Label lblOrder = new Label("Save The Products In:");
		RadioButton rbAscend = new RadioButton("Ascending Order");
		RadioButton rbDescend = new RadioButton("Descending Order");
		RadioButton rbInsert = new RadioButton("Inserting Order");

		ToggleGroup tgOrder = new ToggleGroup();
		rbAscend.setToggleGroup(tgOrder);
		rbDescend.setToggleGroup(tgOrder);
		rbInsert.setToggleGroup(tgOrder);

		Button btSubmit = new Button("Submit");
		btSubmit.setDisable(true);

		for (Toggle rb : tgOrder.getToggles()) {
			((RadioButton) rb).setOnAction(e -> {
				btSubmit.setDisable(false);
			});
		}

		btSubmit.setOnAction(e -> {
			String order = ((RadioButton) tgOrder.getSelectedToggle()).getText();
			for (UIEventsListener listener : listeners) {
				listener.selectedOrderInUI(order);
			}
		});

		gpSetOrder.addColumn(0, lblOrder, rbAscend, rbDescend, rbInsert);
		gpSetOrder.add(btSubmit, 4, 3);
		gpSetOrder.setAlignment(Pos.CENTER);
	}


	private void setAddProduct() {
		gpAddProduct = setGridPane();

		Label lblCatalogNum = new Label("Catalog Number");
		TextField txtCatalogNum = new TextField();

		Label lblProductName = new Label("Product Name");
		TextField txtProductName = new TextField();

		Label lblCost = new Label("Cost Price");
		TextField txtCost = new TextField();

		Label lblPrice = new Label("Consumer Price");
		TextField txtPrice = new TextField();

		Label lblCustomerName = new Label("Customer Name");
		TextField txtCustomerName = new TextField();

		Label lblPhone = new Label("Phone Number");
		TextField txtPhone = new TextField();

		Label lblUpdate = new Label("Would Like To Receive Promotional Updates");
		RadioButton rbYes = new RadioButton("Yes");
		RadioButton rbNo = new RadioButton("No");
		ToggleGroup tgUpdate = new ToggleGroup();
		rbYes.setToggleGroup(tgUpdate);
		rbNo.setToggleGroup(tgUpdate);
		rbYes.setSelected(true);
		HBox hbChoice = setHBox();
		hbChoice.getChildren().addAll(rbYes, rbNo);

		Button btSubmit = new Button("Submit");

		btSubmit.setOnAction(e -> {
			String catalogNum = txtCatalogNum.getText();
			String productName = txtProductName.getText();
			String customerName = txtCustomerName.getText();
			String phone = txtPhone.getText();
			boolean isUpdate = true;
			if (rbNo.isSelected()) {
				isUpdate = false;
			}
			int cost = 0;
			int price = 0;
			try {
				cost = Integer.parseInt(txtCost.getText());
				price = Integer.parseInt(txtPrice.getText());
				for (UIEventsListener listener : listeners) {
					listener.addProductToUI(catalogNum, productName, cost, price, customerName, phone, isUpdate);
				}

			} catch (NumberFormatException exp) {
				showErrorMessage("The price must be an integer");
			}
		});

		Button btUndo = new Button("Undo");

		btUndo.setOnAction(e -> {
			for (UIEventsListener listener : listeners) {
				listener.undoAddProductInUI();
			}
		});

		HBox hbox = setHBox();
		hbox.getChildren().addAll(btSubmit, btUndo);
		
		gpAddProduct.addColumn(0, lblCatalogNum, lblProductName, lblCost, lblPrice);
		gpAddProduct.addColumn(1, txtCatalogNum, txtProductName, txtCost, txtPrice);
		gpAddProduct.addColumn(2, lblCustomerName, lblPhone, lblUpdate);
		gpAddProduct.addColumn(3, txtCustomerName, txtPhone, hbChoice);
		gpAddProduct.add(hbox, 3, 4);

		gpAddProduct.setAlignment(Pos.CENTER_RIGHT);
	}
	
	private void setFindProduct() {
		gpFindProduct = setGridPane();

		Label lblCatalogNum = new Label("Catalog Number");
		TextField txtCatalogNum = new TextField();
		Button btSearch = new Button("Search");

		btSearch.setOnAction(e -> {
			lblProduct.setText("");
			String catalogNum = txtCatalogNum.getText();
			for (UIEventsListener listener : listeners) {
				listener.findProductInUI(catalogNum);
			}
		});

		lblProduct = new Label();

		gpFindProduct.addRow(0, lblCatalogNum, txtCatalogNum, btSearch);
		gpFindProduct.add(lblProduct, 0, 1);
		gpFindProduct.setAlignment(Pos.CENTER);
	}
	
	private void setAllProducts() {
		hbAllProducts = setHBox();
		hbAllProducts.setAlignment(Pos.CENTER);
	}

	private void setProfits() {
		hbProfits = setHBox();
		hbProfits.setAlignment(Pos.CENTER);
	}

	private void setTotalProfit() {
		lblTotalProfit = new Label();
		lblTotalProfit.setAlignment(Pos.CENTER);
	}
	
	private void setCustomers() {
		vbCustomers = setVBox();
		vbCustomers.setAlignment(Pos.CENTER);
	}
	
	private void setSendMessage() {
		hbSendMessage = setHBox();
		hbSendMessage.setVisible(false);

		Label lblMessage = new Label("Enter message:");
		TextField txtMessage = new TextField();
		txtMessage.setMinSize(600, 200);
		Button btSend = new Button("Send");

		btSend.setOnAction(e -> {
			for (UIEventsListener listener : listeners) {
				listener.sendMessageInUI(txtMessage.getText());
			}
		});

		hbSendMessage.getChildren().addAll(lblMessage, txtMessage, btSend);
		hbSendMessage.setAlignment(Pos.CENTER);
	}
	
	private void setRemoveProduct() {
		hbRemoveProduct = setHBox();
		hbRemoveProduct.setVisible(false);
		
		Label lblCatalogNum = new Label("Catalog Number");
		TextField txtCatalogNum = new TextField();
		Button btDelete = new Button("Delete");

		btDelete.setOnAction(e -> {
			String catalogNum = txtCatalogNum.getText();
			for (UIEventsListener listener : listeners) {
				listener.removeProductInUI(catalogNum);
			}
		});
		
		hbRemoveProduct.getChildren().addAll(lblCatalogNum, txtCatalogNum, btDelete);
		hbRemoveProduct.setAlignment(Pos.CENTER);
	}
	
	private VBox setVBox() {
		VBox vBox = new VBox();
		vBox.setSpacing(30);
		vBox.setPadding(new Insets(20));
		return vBox;
	}

	private HBox setHBox() {
		HBox hBox = new HBox();
		hBox.setSpacing(30);
		hBox.setPadding(new Insets(20));
		return hBox;
	}

	private GridPane setGridPane() {
		GridPane gridPane = new GridPane();
		gridPane.setVgap(45);
		gridPane.setHgap(40);
		gridPane.setVisible(false);
		return gridPane;
	}

	private void initText(Pane pane) {
		for (Node node : pane.getChildren()) {
			if (node instanceof TextField) {
				((TextField) node).setText("");
			}
		}
	}

	private void setAllNotVisible() {
		for (Node node : spLayout.getChildren()) {
			node.setVisible(false);
		}
	}
	
	private Label[] arrayToLabels(String[] array) {
		Label[] labels = new Label[array.length];
		for (int i = 0; i < array.length; i++) {
			labels[i] = new Label(array[i]);
		}
		return labels;
	}
	
	@Override
	public void orderSetMessage() {
		gpSetOrder.setVisible(false);
		JOptionPane.showMessageDialog(null, "The products saving order was successfully determined");
	}

	@Override
	public void addProductToUI(String catalogNum) {
		JOptionPane.showMessageDialog(null, String.format("Product %s added successfully", catalogNum));
		initText(gpAddProduct);
	}

	@Override
	public void removedProductMessage(String catalogNum) {
		JOptionPane.showMessageDialog(null, String.format("Product %s removed successfully", catalogNum));
		gpAddProduct.setVisible(false);
	}

	@Override
	public void foundProductMessage(String product) {
		lblProduct.setText(product);
	}

	@Override
	public void showAllProductsInUI(String[] products) {
		hbAllProducts.getChildren().clear();
		hbAllProducts.getChildren().addAll(arrayToLabels(products));
	}

	@Override
	public void showProfitsInUI(String[] profits) {
		hbProfits.getChildren().clear();
		hbProfits.getChildren().addAll(arrayToLabels(profits));
	}

	@Override
	public void showTotalProfitInUI(int profit) {
		lblTotalProfit.setText("Total profit: " + profit);
	}

	@Override
	public void messageArrived() {
		JOptionPane.showMessageDialog(null, "Message sent");
		initText(hbSendMessage);
		hbSendMessage.setVisible(false);
	}

	@Override
	public void showErrorMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	@Override
	public void showApprovedCustomer(String name) {
		vbCustomers.getChildren().add(new Label(name));
	}

	@Override
	public void finishShowApprovedCustomers() {
		JOptionPane.showMessageDialog(null, "Customers display is complete");
		vbCustomers.getChildren().clear();
	}

	@Override
	public void removeAllProductsMessage() {
		JOptionPane.showMessageDialog(null, "All products have been deleted");
	}

}
