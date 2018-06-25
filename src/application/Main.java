package application;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * Glowna klasa aplikacji.
 *
 * @author Kicior
 *
 */
public class Main extends Application {

	private static BorderPane root = new BorderPane();

	public static BorderPane getRoot() {
		return root;
	}

	/**
	 * Metoda tworzaca okno aplikacji.
	 */
	public void start(Stage primaryStage) {
		try {
			URL paneOneURL = getClass().getResource("/FXML/Login.fxml");
			AnchorPane paneOne = FXMLLoader.load(paneOneURL);
			root.setCenter(paneOne);
		
			Scene scene = new Scene(root, 600, 300);
			scene.getStylesheets().add(getClass().getResource("/FXML/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metoda urchamiajaca aplikacje okienkowa.
	 *
	 * @param args
	 *            Glowny argument funkcji.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
