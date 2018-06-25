package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Klasa odwzorywujaca Cwiczenie.
 *
 * @author Kicior
 *
 */
public class WorkoutFX {

	final private SimpleIntegerProperty id;
	final private SimpleStringProperty name;
	final private SimpleStringProperty type;

	/**
	 * Konstruktor klasy.
	 *
	 * @param id
	 *            Numer id cwiczenia
	 * @param name
	 *            Nazwa cwiczenia
	 * @param type
	 *            Typ cwiczenia. Podzial na parite.
	 */
	public WorkoutFX(Integer id, String name, String type) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.type = new SimpleStringProperty(type);
	}

	public Integer getId() {
		return id.get();
	}

	public String getName() {
		return name.get();
	}

	public String getType() {
		return type.get();
	}

	@Override
	public String toString() {
		return name.getValue();
	}

}
