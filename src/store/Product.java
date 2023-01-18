package store;

import java.io.Serializable;
import exceptions.NameException;
import exceptions.PriceException;

public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private int cost;
	private int price;
	private Customer customer;
	
	public Product(String name, int cost, int price, Customer customer) throws NameException, PriceException {
		setName(name);
		setCost(cost);
		setPrice(price);
		setCustomer(customer);
	}
	
	public void setName(String name) throws NameException {
		if (name.trim().isEmpty()) {
			throw new NameException();
		}
		this.name = name;
	}
	
	public void setCost(int cost) throws PriceException {
		checkPrice(cost);
		this.cost = cost;
	}
	
	public void setPrice(int price) throws PriceException {
		checkPrice(price);
		this.price = price;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	private void checkPrice(int price) throws PriceException {
		if (price < 0) {
			throw new PriceException();
		}
	}
	
	public int getProfit() {
		return price - cost;
	}

	public String getName() {
		return name;
	}

	public Customer getCustomer() {
		return customer;
	}
	
	@Override
	public String toString() {
		return String.format("Product: %s \nCost Price: %d NIS \nSold For: %d NIS \n", name, cost, price);
	}
	
}
