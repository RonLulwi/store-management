package command;

import store.Store;

public class ShowTotalProfitCommand implements Command {

	Store store;
	int profit;
	
	public ShowTotalProfitCommand(Store store) {
		this.store = store;
		profit = 0;
	}
	
	@Override
	public void execute() {
		profit = store.getTotalProfit();
	}
	
	public int getProfit() {
		return profit;
	}

}
