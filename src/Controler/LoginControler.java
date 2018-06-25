package Controler;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import application.Baza;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

/**
 * Klasa kontolna do pliku Login.fxml.
 * @author Kicior
 *
 */
public class LoginControler {
	public Baza loginModel = new Baza();

	@FXML
	private TextField txtusername;
	@FXML
	private TextField txtpassword;

/**
 *  Metoda wykonujaca logowanie.
 * @param event Zdarzenie wywowolujace metode.
 * @throws IOException Wyj¹tek
 */
	public void Login(ActionEvent event) throws IOException {
		try {
			loginModel.openConnection();
			if (loginModel.isLogin(txtusername.getText(), txtpassword.getText())) {
				loginModel.closeConnection();
				changeSceen(event);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
/**
 * Funkcja zmieniaj¹ca scene w ekranie glownym.
 * @param event Zdarzenie wywowolujace metode.
 * @throws IOException Wyj¹tek
 */
	public void changeSceen(ActionEvent event) throws IOException {
		URL menuURL = getClass().getResource("/FXML/Menu.fxml");
		AnchorPane menu = FXMLLoader.load(menuURL);
		URL paneOneURL = getClass().getResource("/FXML/PaneOne.fxml");
		AnchorPane paneOne = FXMLLoader.load(paneOneURL);
		BorderPane border = Main.getRoot();
		border.setLeft(menu);
		border.setCenter(paneOne);
	}
}
