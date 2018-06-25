package Controler;

import java.io.IOException;
import java.net.URL;

import application.Baza;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
/**
 * Klasa kontolna do pliku Menu.fxml.
 * @author Kicior
 *
 */
public class MenuControler {


	@FXML
	private Label nickname;
	@FXML
	private ImageView image_menu;
	/**
	 * Inicjalizacja sceny Menu.fxml
	 */
	@FXML
	public void initialize() {
		nickname.setText(Baza.getUser().getUsername());
		String url = Baza.getUser().getUrl();
		image_menu.setImage(new Image(url));
	}
	/**
	 * Funkcja zmieniaj¹ca scene w ekranie glownym.
	 * @param event Zdarzenie wywowolujace metode.
	 * @throws IOException Wyj¹tek
	 */
	public void changeSceen(ActionEvent event) throws IOException {
		String value = ((Button) event.getSource()).getText();
		URL paneURL = null;

		switch (value) {
		case "Dodaj":
			paneURL = getClass().getResource("/FXML/PaneOne.fxml");
			break;
		case "Historia":
			paneURL = getClass().getResource("/FXML/PaneTwo.fxml");
			break;
		case "Plan":
			paneURL = getClass().getResource("/FXML/PaneThree.fxml");
			break;
		case "Statystyki":
			paneURL = getClass().getResource("/FXML/PaneFour.fxml");
			break;
		case "Logout":
			paneURL = getClass().getResource("/FXML/Login.fxml");
			BorderPane border = Main.getRoot();
			border.setLeft(null);
			break;
		}
		AnchorPane paneNext = FXMLLoader.load(paneURL);
		BorderPane border = Main.getRoot();
		border.setCenter(paneNext);
	}
}
