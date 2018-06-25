package Controler;

import application.Baza;
import application.TreningFX;
import application.WorkoutFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Klasa kontrolna do pliku PaneThree.fxml. Okno zawiera plany treningowe wraz z
 * mozliwoscia dodawania treningow.
 *
 * @author Kicior
 *
 */
public class PaneThreeControler {
	/**
	 * Obiekt do obslugi bazy danych.
	 */
	Baza baza = new Baza();
	/**
	 * Lista obiektów klasy TreningFX
	 */
	private ObservableList<TreningFX> treninglist;

	@FXML
	private Label status;
	@FXML
	private Label status2;
	@FXML
	private TextField txtseries1;
	@FXML
	private TextField txtamount1;
	@FXML
	private TextField txtweight1;
	@FXML
	private TextField txtseries2;
	@FXML
	private TextField txtamount2;
	@FXML
	private TextField txtweight2;
	@FXML
	private TextField txtseries3;
	@FXML
	private TextField txtamount3;
	@FXML
	private TextField txtweight3;
	@FXML
	private TextField txtseries4;
	@FXML
	private TextField txtamount4;
	@FXML
	private TextField txtweight4;
	@FXML
	private TextField txtseries5;
	@FXML
	private TextField txtamount5;
	@FXML
	private TextField txtweight5;
	@FXML
	private TextField txtseries6;
	@FXML
	private TextField txtamount6;
	@FXML
	private TextField txtweight6;

	/**
	 * Inicjalizacja nowej sceny.
	 */
	@FXML
	void initialize() {
		treninglist = FXCollections.observableArrayList();
	}

	/**
	 * Funkcja wywolywana ActionEvent. Pobiera informacje z pol tekstowych
	 * TextField i dodaje trening typu PULL do listy treningowej. Trening
	 * wczesniej zdefiniowany!
	 *
	 * @param event
	 *            Zdarzenie wywolujace funckje.
	 */

	public void addTrainingPUSH(ActionEvent event) {

		WorkoutFX workout1 = baza.getWorkoutone(2);
		WorkoutFX workout2 = baza.getWorkoutone(4);
		WorkoutFX workout3 = baza.getWorkoutone(5);

		if (addWorkout(workout1, txtseries1, txtamount1, txtweight1)
				& addWorkout(workout2, txtseries2, txtamount2, txtweight2)
				& addWorkout(workout3, txtseries3, txtamount3, txtweight3)) {
			status.setText("Dodano");
			baza.addTraining(this.treninglist);
			treninglist.clear();
			txtseries1.setText(null);
			txtamount1.setText(null);
			txtweight1.setText(null);
			txtseries2.setText(null);
			txtamount2.setText(null);
			txtweight2.setText(null);
			txtseries3.setText(null);
			txtamount3.setText(null);
			txtweight3.setText(null);
		} else
			status.setText("Blad");

	}

	/**
	 * Funkcja wywolywana ActionEvent . Pobiera informacje z pol tekstowych
	 * TextField i dodaje trening typu PULL do listy treningowej. Trening
	 * wczesniej zdefiniowany!
	 *
	 * @param event
	 *            Zdarzenie wywolujace funckje
	 */
	public void addTrainingPULL(ActionEvent event) {

		WorkoutFX workout1 = baza.getWorkoutone(1);
		WorkoutFX workout2 = baza.getWorkoutone(7);
		WorkoutFX workout3 = baza.getWorkoutone(8);

		if (addWorkout(workout1, txtseries4, txtamount4, txtweight4)
				& addWorkout(workout2, txtseries5, txtamount5, txtweight5)
				& addWorkout(workout3, txtseries6, txtamount6, txtweight6)) {
			status2.setText("Dodano");
			baza.addTraining(this.treninglist);
			treninglist.clear();
			txtseries4.setText(null);
			txtamount4.setText(null);
			txtweight4.setText(null);
			txtseries5.setText(null);
			txtamount5.setText(null);
			txtweight5.setText(null);
			txtseries6.setText(null);
			txtamount6.setText(null);
			txtweight6.setText(null);
		} else
			status2.setText("Blad");

	}

	/**
	 * Funckja dodaj¹ca obiekt WorkoutFX, wraz z innymi wymaganymi parametrami
	 * do listy listy treningowej treninglist
	 *
	 * @param workout
	 *            Obiekt WorkoutFX zawieraj¹cy dane o pojedyñczym cwiczeniu
	 * @param txtseries
	 *            Obiekt TextField zawieraj¹cy wpisany tekxt o liczbie serii
	 * @param txtamount
	 *            Obiekt TextField zawieraj¹cy wpisany tekxt o liczbie powtórzen
	 * @param txtweight
	 *            Obiekt TextField zawieraj¹cy wpisany tekxt o wartosci ciezaru
	 * @return Informacja czy nast¹pilo dodanie elementu
	 */
	public boolean addWorkout(WorkoutFX workout, TextField txtseries, TextField txtamount, TextField txtweight) {

		if (isNumeric(txtamount.getText()) & isNumeric(txtseries.getText()) & isNumeric(txtweight.getText())) {
			Integer amount = Integer.parseInt(txtamount.getText());
			Integer series = Integer.parseInt(txtseries.getText());
			Integer weight = Integer.parseInt(txtweight.getText());
			treninglist.add(new TreningFX(workout, amount, series, weight));
			return true;

		} else
			return false;
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
