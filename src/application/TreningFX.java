package application;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Klasa odwzorywujaca Trening.
 *
 * @author Kicior
 *
 */
public class TreningFX {

	final ObjectProperty<WorkoutFX> workout;
	final SimpleStringProperty name;
	final SimpleIntegerProperty amount;
	final SimpleIntegerProperty series;
	final SimpleIntegerProperty weight;

	/**
	 * Konstruktor klasy.
	 *
	 * @param w
	 *            Obiekt klasy WorkoutFX.
	 * @param amount
	 *            Liczba powtorzen
	 * @param series
	 *            Liczba serii
	 * @param weight
	 *            Cie¿ar
	 */
	public TreningFX(WorkoutFX w, Integer amount, Integer series, Integer weight) {
		super();
		this.workout = new SimpleObjectProperty<WorkoutFX>(w);
		this.name = new SimpleStringProperty(w.getName());
		this.amount = new SimpleIntegerProperty(amount);
		this.series = new SimpleIntegerProperty(series);
		this.weight = new SimpleIntegerProperty(weight);

	}

	public Integer getSeries() {
		return series.get();
	}

	public Integer getAmount() {
		return amount.get();
	}

	public Integer getWeight() {
		return weight.get();
	}

	public String getName() {
		return name.get();
	}

}
