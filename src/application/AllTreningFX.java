package application;

import javafx.beans.property.SimpleIntegerProperty;

import javafx.beans.property.SimpleStringProperty;

/**
 * Klasa odwzorywujaca caly trening. Zestaw cwiczen wraz z parametrami.
 */

public class AllTreningFX {

	final private SimpleStringProperty name;
	final private SimpleStringProperty type;
	final private SimpleIntegerProperty amount;
	final private SimpleIntegerProperty series;
	final private SimpleIntegerProperty day;
	final private SimpleIntegerProperty mounth;
	final private SimpleIntegerProperty weight;
	final private SimpleIntegerProperty id;

	int value = 0;

	/**
	 * Konstruktor klasy.
	 *
	 * @param name
	 *            Nazwa cwiczenia
	 * @param type
	 *            Partia cwiczenia.
	 * @param amount
	 *            Liczba powtorzeñ.
	 * @param series
	 *            Liczba serii.
	 * @param day
	 *            Dzieñ wykonania treningu.
	 * @param mounth
	 *            Miesiac wykonania treningu.
	 * @param weight
	 *            Ciezar podniesiony.
	 * @param id
	 *            Id calego treningu.
	 */
	public AllTreningFX(String name, String type, Integer amount, Integer series, Integer day, Integer mounth,
			Integer weight, Integer id) {
		super();
		this.name = new SimpleStringProperty(name);
		this.type = new SimpleStringProperty(type);
		this.amount = new SimpleIntegerProperty(amount);
		this.series = new SimpleIntegerProperty(series);
		this.day = new SimpleIntegerProperty(day);
		this.mounth = new SimpleIntegerProperty(mounth);
		this.weight = new SimpleIntegerProperty(weight);
		this.id = new SimpleIntegerProperty(id);

	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name.get();
	}

	public String getType() {
		return type.get();
	}

	public Integer getId() {
		return id.get();
	}

	public Integer getAmount() {
		return amount.get();
	}

	public Integer getSeries() {
		return series.get();
	}

	public Integer getDay() {
		return day.get();
	}

	public Integer getWeight() {
		return weight.get();
	}

	public Integer getMounth() {
		return mounth.get();
	}
}
