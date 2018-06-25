/**
 *Paczka zawieraj¹ca klasy kontrolne do plikow FXML.
 */
package Controler;

import application.Baza;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ComboBox;

/**
 * Klasa kontrolna do pliku PaneFour.fxml. Okno zawiera statystki treningowe.
 *
 * @author Kicior
 *
 */
public class PaneFourControler {

	Baza baza = new Baza();
	private ObjectProperty<String> typeChoose;
	private ObjectProperty<String> monthChoose;
	private ObservableList<String> typelist;
	private ObservableList<String> monthlist;

	@FXML
	private LineChart<Integer, Integer> lineChart;
	@FXML
	private ComboBox<String> choosetype;
	@FXML
	private ComboBox<String> choosemonth;
	@FXML
	private NumberAxis xAxis;
	@FXML
	private NumberAxis yAxis;

	/**
	 * Inicjalizacja nowej sceny.
	 */
	@FXML
	void initialize() {
		typeChoose = new SimpleObjectProperty<>();
		monthChoose = new SimpleObjectProperty<>();
		typeChoose.set("All");
		monthChoose.set("11");
		typelist = baza.getType();
		monthlist = baza.getMonth();
		choosemonth.setItems(this.monthlist);
		choosetype.setItems(this.typelist);
		choosemonth.getSelectionModel().select(0);
		choosetype.getSelectionModel().select(0);
		xAxis.setLabel("Dzieñ");
		yAxis.setLabel("Objtosc");
		xAxis.setTickUnit(2);
		yAxis.setTickUnit(10);
		xAxis.setLowerBound(1);
		xAxis.setUpperBound(31);
		//lineChart.getData().add(baza.getValue(typeChoose.get(), monthChoose.get()));
	}
	/**
	 * Metoda zapisujaca wybrany obiekt z ComboBox choosetype do zmiennej
	 * typeChoose. Metoda aktualizuje wykres liniowy.
	 *
	 * @param event
	 *            Zdarzenie wywolujace funckje.
	 */
	public void chooseType(ActionEvent event) {
		typeChoose.set(choosetype.getSelectionModel().getSelectedItem());
		//lineChart.getData().add(baza.getValue(typeChoose.get(), monthChoose.get()));
	}
	/**
	 * Metoda zapisujaca wybran¹ date z ComboBox choosetype do zmiennej
	 * monthChoose. Metoda aktualizuje wykres liniowy.
	 *
	 * @param event
	 *            Zdarzenie wywolujace funckje.
	 */
	public void chooseMonth(ActionEvent event) {
		monthChoose.set(choosemonth.getSelectionModel().getSelectedItem());
		//lineChart.getData().add(baza.getValue(typeChoose.get(), monthChoose.get()));
	}
	/**
	 * Metoda dodajaca dane do wykresu.
	 * @param event Zdarzenie wywolujace funckje.
	 */

	public void addChart(ActionEvent event) {
		lineChart.getData().add(baza.getValue(typeChoose.get(), monthChoose.get()));
	}
	/**
	 * Metoda usuwajaca dane z wykresu.
	 * @param event Zdarzenie wywolujace funckje.
	 */
	public void deleteChart(ActionEvent event) {
		lineChart.getData().clear();
	}

}
