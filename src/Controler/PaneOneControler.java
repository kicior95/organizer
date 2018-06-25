package Controler;

import application.Baza;
import application.TreningFX;
import application.WorkoutFX;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Klasa kontrolna do pliku PaneOne.fxml. Okno zawiera interfejs do dodawania
 * treningow.
 *
 * @author Kicior
 *
 */
public class PaneOneControler {
	Baza baza = new Baza();

	private ObservableList<String> typelist;
	private ObservableList<WorkoutFX> workoutlist;
	private ObservableList<TreningFX> treninglist;
	private ObjectProperty<String> typeChoose;
	private ObjectProperty<WorkoutFX> workoutChoose;

	@FXML
	private TableView<TreningFX> tabela;
	@FXML
	private TableColumn<TreningFX, String> name;
	@FXML
	private TableColumn<TreningFX, Integer> series;
	@FXML
	private TableColumn<TreningFX, Integer> amount;
	@FXML
	private TableColumn<TreningFX, Integer> day;
	@FXML
	private TableColumn<TreningFX, Integer> month;
	@FXML
	private TableColumn<TreningFX, Integer> weight;
	@FXML
	private ComboBox<WorkoutFX> chooseworkout;
	@FXML
	private ComboBox<String> choosetype;
	@FXML
	private Button addWorkout;
	@FXML
	private TextField txtseries;
	@FXML
	private TextField txtamount;
	@FXML
	private TextField txtweight;
	@FXML
	private Label status;

	/**
	 * Inicjalizacja nowej sceny.
	 */
	@FXML
	void initialize() {
		name.setCellValueFactory(new PropertyValueFactory<TreningFX, String>("name"));
		series.setCellValueFactory(new PropertyValueFactory<TreningFX, Integer>("series"));
		amount.setCellValueFactory(new PropertyValueFactory<TreningFX, Integer>("amount"));
		weight.setCellValueFactory(new PropertyValueFactory<TreningFX, Integer>("weight"));

		typeChoose = new SimpleObjectProperty<>();
		typeChoose.set("All");
		workoutChoose = new SimpleObjectProperty<>();
		workoutChoose.set(null);

		typelist = baza.getType();
		workoutlist = baza.getWorkout(typeChoose.get());
		treninglist = FXCollections.observableArrayList();

		chooseworkout.setItems(this.workoutlist);
		choosetype.setItems(this.typelist);
		choosetype.getSelectionModel().select(0);
		tabela.setItems(this.treninglist);
		status.setText("Status");

	}

	/**
	 * Metoda zapisujaca wybrany obiekt z ComboBox chooseworkout do zmiennej
	 * workoutChoose.
	 *
	 * @param event
	 *            Zdarzenie wywolujace funckje.
	 */
	public void chooseWorkout(ActionEvent event) {
		workoutChoose.set(chooseworkout.getSelectionModel().getSelectedItem());
	}

	/**
	 * Metoda zapisujaca wybrany obiekt z ComboBox choosetype do zmiennej
	 * typeChoose. Metoda aktualizuje liste wyboru workoutlist
	 *
	 * @param event
	 *            Zdarzenie wywolujace funckje.
	 */
	public void chooseType(ActionEvent event) {
		typeChoose.set(choosetype.getSelectionModel().getSelectedItem());
		workoutChoose.set(null);
		workoutlist.clear();
		workoutlist.addAll(baza.getWorkout(typeChoose.get()));
	}

	/**
	 * Metoda dodaj¹ca do listy treningowej wybrane cwiczenie wrac z parametrami
	 * treningowymi.
	 *
	 * @param event
	 *            Zdarzenie wywolujace funckje.
	 */
	public void addWorkout(ActionEvent event) {
		if (workoutChoose.get() != null & isNumeric(txtamount.getText()) & isNumeric(txtseries.getText())
				& isNumeric(txtweight.getText())) {
			Integer amount = Integer.parseInt(txtamount.getText());
			Integer series = Integer.parseInt(txtseries.getText());
			Integer weight = Integer.parseInt(txtweight.getText());
			treninglist.add(new TreningFX(workoutChoose.get(), amount, series, weight));
			status.setText("Dodano");
		} else
			status.setText("Blad");
	}

	/**
	 * Metoda dodaj¹ca do bazy danych utworzony trening.
	 *
	 * @param event
	 *            Zdarzenie wywolujace funckje.
	 */
	public void addTraining(ActionEvent event) {

		baza.addTraining(this.treninglist);
		treninglist.clear();

	}

	/**
	 * Funkcja sprawdzaj¹ca czy dany obiekt typu String jest wartoœci¹
	 * numeryczn¹ dodatni¹.
	 *
	 * @param str
	 *            Sprawdzany obiekt String
	 * @return Czy obiekt jest wartoœci¹ numeryczn¹ dodatnia.
	 */
	public static boolean isNumeric(String str) {
		Double w;
		try {
			w = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		if (w >= 0) {
			return true;
		} else
			return false;

	}

}
