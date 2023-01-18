package observer;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import exceptions.MessageEmptyException;
import exceptions.NoApprovedCustomersException;

public class Sender {
	
	private static final Sender INSTANCE = new Sender();
	private Set<Observer> set = new HashSet<>();
	private Vector<String> names = new Vector<String>();
	
	private Sender() {
		
	}
	
	public static Sender getInstance() {
		return INSTANCE;
	}
	
	public void attach(Observer observer) {
		set.add(observer);
	}

	public void notify(String msg) throws MessageEmptyException {
		if (msg.trim().isEmpty())
			throw new MessageEmptyException();
		
		names.clear();
		for (Observer observer : set) {
			names.add(observer.update(msg));
		}
	}
	
	public Vector<String> getNames() throws NoApprovedCustomersException {
		if (names.size() == 0) {
			throw new NoApprovedCustomersException();
		}
		return names;
	}

}
