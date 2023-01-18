package store;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;
import exceptions.CatalogNumberException;
import exceptions.OrderAlreadySetException;
import exceptions.OrderNotSetException;
import exceptions.MessageEmptyException;
import exceptions.NoApprovedCustomersException;
import exceptions.NoProductsException;
import exceptions.ProductAlredyRemovedException;
import exceptions.ProductNotExistException;
import file.MyFile;
import listeners.ModelEventsListener;
import observer.Sender;

public class Store {

	public enum Order {
		ASCEND, DESCEND, INSERT
	}

	public final String FILE_NAME = "products.txt";
	private MyFile myFile;

	private Vector<ModelEventsListener> listeners;

	private Map<String, Product> products;
	private boolean orderSet;

	private Sender sender;

	private String lastProduct;

	public Store() {
		listeners = new Vector<ModelEventsListener>();
		products = null;
		sender = Sender.getInstance();
		orderSet = false;
		lastProduct = "";
		
		myFile = new MyFile(FILE_NAME);
		if (myFile.exist()) {
			readStoreFromFile();
		} 
	}

	public void attachListener(ModelEventsListener listener) {
		listeners.add(listener);
	}

	private void readStoreFromFile() {
		try {
			setOrder(myFile.getOrder());
		} catch (OrderAlreadySetException e) {
			e.printStackTrace();
		}

		Iterator<Entry<String, Product>> it = myFile.iterator();
		Entry<String, Product> entry;
		
		while (it.hasNext()) {
			entry = it.next();
			updateMap(entry.getKey(), entry.getValue());
		}
	}

	public void setOrder(Order order) throws OrderAlreadySetException {
		if (orderSet) {
			throw new OrderAlreadySetException();
		}

		Comparator<String> comparator = null;

		switch (order) {
		case DESCEND:
			comparator = new Comparator<String>() {
				@Override
				public int compare(String str1, String str2) {
					return str2.compareTo(str1);
				}
			};
			break;
		case INSERT:
			comparator = new Comparator<String>() {
				@Override
				public int compare(String str1, String str2) {
					if (str1.compareTo(str2) == 0)
						return 0;
					return 1;
				}
			};
			break;
		default:
			break;
		}

		products = new TreeMap<String, Product>(comparator);
		if (!myFile.exist()) {
			myFile.writeOrder(order);
		}
		orderSet = true;

		for (ModelEventsListener listener : listeners) {
			listener.setOrderInModel();
		}
	}
	
	private void updateMap(String catalogNum, Product product) {
		products.put(catalogNum, product);
		lastProduct = catalogNum;

		if (product.getCustomer().getUpdate()) {
			sender.attach(product.getCustomer());
		}
	}

	public void addProduct(String catalogNum, Product product) throws CatalogNumberException, OrderNotSetException {
		if (products == null) {
			throw new OrderNotSetException();
		}

		if (catalogNum.trim().isEmpty())
			throw new CatalogNumberException();

		updateMap(catalogNum, product);
		myFile.writeProduct(catalogNum, product);
		
		for (ModelEventsListener listener : listeners) {
			listener.addProductToModel(catalogNum);
		}
	}

	public Product getProduct(String catalogNum) throws ProductNotExistException, NoProductsException {
		if (products == null) {
			throw new NoProductsException();
		}
		
		Product product = products.get(catalogNum);
		if (product == null) {
			throw new ProductNotExistException();
		}
		
		return product;
	}

	public void removeProduct(String catalogNum) throws NoProductsException, ProductNotExistException {
		if (products == null || getNumOfProducts() == 0) {
			throw new NoProductsException();
		}
		
		if (products.remove(catalogNum) == null) {
			throw new ProductNotExistException();
		}

		Iterator<Entry<String, Product>> it = myFile.iterator();
		Map.Entry<String, Product> entry;
		
		while (it.hasNext()) {
			entry = it.next();
			if (entry.getKey().equals(catalogNum)) {
				//it.remove();
				break;
			}
		}
		
		for (ModelEventsListener listener : listeners) {
			listener.removeProductFromModel(catalogNum);
		}
	}

	public void removeAllProducts() throws NoProductsException {
		if (products == null || getNumOfProducts() == 0) {
			throw new NoProductsException();
		}
		
		Iterator<Entry<String, Product>> it = myFile.iterator();
		
		while (it.hasNext()) {
			products.remove(it.next().getKey());
			//it.remove();
		}
		
		for (ModelEventsListener listener : listeners) {
			listener.removeAllProductsFromModel();
		}
	}

	public Map<String, Product> getAllProducts() throws NoProductsException {
		if (products == null || products.size() == 0) {
			throw new NoProductsException();
		}
		
		return products;
	}
	
	public int getNumOfProducts() {
		return products.size();
	}

	public int getTotalProfit() {
		int profit = 0;
		for (Entry<String, Product> entry : products.entrySet()) {
			profit = profit + entry.getValue().getProfit();
		}
		return profit;
	}
	
	public void sendMessage(String msg) throws MessageEmptyException {
		sender.notify(msg);
	}
	
	public Vector<String> getResponses() throws NoApprovedCustomersException {
		return sender.getNames();
	}

	public void setMemento(Memento m) throws ProductAlredyRemovedException, NoProductsException {
		if (m.getCatalogNum().isEmpty()) {
			throw new NoProductsException();
		}
		
		Product product = products.remove(m.getCatalogNum());
		if (product == null) {
			throw new ProductAlredyRemovedException();
		}
		
		for (ModelEventsListener listener : listeners) {
			listener.removeProductFromModel(m.getCatalogNum());
		}
	}

	public Memento createMemento() {
		return new Memento(lastProduct);
	}

	public static class Memento {
		private String catalogNum;

		private Memento(String catalogNum) {
			this.catalogNum = catalogNum;
		}

		private String getCatalogNum() {
			return catalogNum;
		}
	}
}
