package store;

import java.io.Serializable;
import exceptions.NameException;
import exceptions.PhoneException;
import observer.Observer;

public class Customer implements Serializable, Observer {

	private static final long serialVersionUID = 1L;
	public transient final int PHONE_LENGTH = 10;
	public transient final String PHONE_PREFIX = "05";

	private String name;
	private String phone;
	private boolean update;

	public Customer(String name, String phone, boolean update) throws NameException, PhoneException {
		setName(name);
		setPhone(phone);
		this.update = update;
	}

	public void setName(String name) throws NameException {
		if (name.trim().isEmpty()) {
			throw new NameException();
		}
		this.name = name;
	}

	public void setPhone(String phone) throws PhoneException {
		if (isValidPhone(phone))
			this.phone = phone;
		else
			throw new PhoneException();
	}

	private boolean isValidPhone(String phone) {
		if (phone.length() != PHONE_LENGTH || !phone.startsWith(PHONE_PREFIX)) {
			return false;
		}

		for (int i = PHONE_PREFIX.length(); i < PHONE_LENGTH; i++) {
			if (!Character.isDigit(phone.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public boolean getUpdate() {
		return update;
	}

	@Override
	public String update(String msg) {
		return name;
	}

	@Override
	public String toString() {
		return name + ", " + phone;
	}
}
