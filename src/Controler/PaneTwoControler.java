package Controler;

import java.time.LocalDate;

import application.AllTreningFX;
import application.Baza;
import application.WorkoutFX;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Klasa kontrolna do pliku PaneTwo.fxml. Okno zawiera interfejs do obslugi
 * historii treningowej.
 *
 * @author Kicior
 *
 */
public class PaneTwoControler {
	Baza baza = new Baza();

	private ObjectProperty<String> typeChoose;
	private ObjectProperty<WorkoutFX> workoutChoose;
	private ObjectProperty<AllTreningFX> alltreningchoose;
	private ObservableList<AllTreningFX> alltreninglist;
	private ObservableList<String> typelist;
	private ObservableList<WorkoutFX> workoutlist;
	private LocalDate data;

	@FXML
	private TableView<AllTreningFX> tabela;
	@FXML
	private TableColumn<AllTreningFX, String> name;
	@FXML
	private TableColumn<AllTreningFX, String> type;
	@FXML
	private TableColumn<AllTreningFX, Integer> series;
	@FXML
	private TableColumn<AllTreningFX, Integer> amount;
	@FXML
	private TableColumn<AllTreningFX, Integer> day;
	@FXML
	private TableColumn<AllTreningFX, Integer> mounth;
	@FXML
	private TableColumn<AllTreningFX, Integer> weight;
	@FXML
	private ComboBox<WorkoutFX> chooseworkout;
	@FXML
	private ComboBox<String> choosetype;
	@FXML
	private DatePicker date;

	/**
	 * Inicjalizacja nowej sceny.
	 */
	@FXML
	void initialize() {

		name.setCellValueFactory(new PropertyValueFactory<AllTreningFX, String>("name"));
		type.setCellValueFactory(new PropertyValueFactory<AllTreningFX, String>("type"));
		series.setCellValueFactory(new PropertyValueFactory<AllTreningFX, Integer>("series"));
		amount.setCellValueFactory(new PropertyValueFactory<AllTreningFX, Integer>("amount"));
		day.setCellValueFactory(new PropertyValueFactory<AllTreningFX, Integer>("day"));
		mounth.setCellValueFactory(new PropertyValueFactory<AllTreningFX, Integer>("mounth"));
		weight.setCellValueFactory(new PropertyValueFactory<AllTreningFX, Integer>("weight"));

		typeChoose = new SimpleObjectProperty<>();
		typeChoose.set("All");
		workoutChoose = new SimpleObjectProperty<>();
		alltreningchoose = new SimpleObjectProperty<>();
		alltreninglist = baza.getTrening(typeChoose.get(), 0, 0, 0);
		typelist = baza.getType();
		workoutlist = baza.getWorkout(typeChoose.get());

		tabela.setItems(this.alltreninglist);
		chooseworkout.setItems(this.workoutlist);
		choosetype.setItems(this.typelist);
		choosetype.getSelectionModel().select(0);

	}

	/**
	 * Metoda zapisujaca wybrany obiekt z ComboBox choosetype do zmiennej
	 * typeChoose. Metoda aktualizuje liste wyboru workoutlist oraz liste
	 * treningow¹ alltreninglist.
	 *
	 * @param event
	 *            Zdarzenie wywolujace funckje.
	 */
	public void chooseType(ActionEvent event) {
		typeChoose.set(choosetype.getSelectionModel().getSelectedItem());
		workoutlist.clear();
		workoutlist.addAll(baza.getWorkout(typeChoose.get()));
		alltreninglist.clear();
		alltreninglist.addAll(baza.getTrening(typeChoose.get(), 1, 0, 0));
	}

	/**
	 * Metoda aktualizujaca liste alltreninglist na podstawie wybranego obiektu
	 * z ComboBoxchooseworkout.
	 *
	 * @param event
	 *            Zdarzenie wywolujace funckje.
	 */
	public void chooseWorkout(ActionEvent event) {
		WorkoutFX tmp = chooseworkout.getSelectionModel().getSelectedItem();
		workoutChoose.set(tmp);
		alltreninglist.clear();
		if (!(tmp == null))
			alltreninglist.addAll(baza.getTrening(workoutChoose.get().getName(), 2, 0, 0));
	}

	/**
	 * Metoda zapisujaca wybrany obiekt z TableViev do zmiennej
	 * alltreningchoose.
	 *
	 * @param event
	 *            Zdarzenie wywolujace funckje.
	 */
	public void chooseTrening(ActionEvent event) {
		alltreningchoose.set(tabela.getSelectionModel().getSelectedItem());
	}

	/**
	 * Metoda zapisujaca wybrany obiekt z Date oraz aktualizowanie listy
	 * treningowej alltreninglist.
	 *
	 * @param event
	 *            Zdarzenie wywolujace funckje.
	 */
	public void chooseDate(ActionEvent event) {
		data = date.getValue();
		int day = data.getDayOfMonth();
		int mounth = data.getMonthValue();

		alltreninglist.clear();
		alltreninglist.addAll(baza.getTrening(null, 3, day, mounth));

	}

	/**
	 * Metoda usuwuj¹ca wybrany obiekt z listy alltreninglst oraz kasujaca
	 * wartoœc z bazy danych.
	 *
	 * @param event
	 *            Zdarzenie wywolujace funckje.
	 */
	public void deleteTrening(ActionEvent event) {
		chooseTrening(null);
		baza.deleteTrening(alltreningchoose.get());
		alltreninglist.remove(alltreningchoose.get());

	}

}
