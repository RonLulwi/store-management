package store;

import javafx.application.Application;
import javafx.stage.Stage;
import view.AbstractView;
import view.View;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Store model = new Store();
		AbstractView view = new View(primaryStage);
		Controller controller = new Controller(model, view);
	}
}
