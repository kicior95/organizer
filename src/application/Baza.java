package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

//TODO Stworzenie klasy BazaModel. Klasa posredniczaca pomiedzy paczka Controler a Baza. Liczne powtorzenia list i zmiennych oraz funkcji zapisujacych, odczytujacych bierzace wybrane obiekty.

/**
 * Glowna klasa logiczna. Odpowiedzialna jest za dzialanie bazy danych oraz
 * obsluge odczyty i zapisu danych z niej.
 *
 * @author Kicior
 *
 */

public class Baza {

	Connection connection = null;
	private Date data = new Date();
	static User user = new User(null, 0, null);

	/**
	 * Metoda otwierajaca polaczenie z baza danych.
	 */
	public void openConnection() {
		connection = SqliteConnection.Connector();

		if (connection == null)
			System.exit(1);
	}

	/**
	 * Metoda zamykajaca polaczenie z baza danych.
	 */
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.err.println("Problem z zamknieciem polaczenia");
			e.printStackTrace();
		}
	}

	/**
	 * Metoda statyczna zwracajaca nazwe zalogowanego uzytkownika.
	 *
	 * @return Nazwa uzytkownika.
	 */
	// TODO Rezygnacja z funkcji na rzecz metody getUser()

	static public String getName() {
		return user.getUsername();

	}

	/**
	 *
	 * @return Zwraca obiekt zawierajacy zalogowanego uzytkownika.
	 */
	static public User getUser() {
		return user;
	}

	/**
	 * Metoda zapisujaca trening do bazy danych. Zapis odbywa sie zgodnie z
	 * wymogami systemu bazy danych.
	 *
	 * @param ID_workout
	 *            Numer id cwiczenia.
	 * @param series
	 *            Liczba serii.
	 * @param amount
	 *            Liczba powtorzen.
	 * @param id_alltrening
	 *            Numer id calego treningu do ktorego nalezy dodawany trening.
	 * @param weight
	 *            Ciezar wykorzystywany w cwiczeniu.
	 * @return Czy nastapilo dodanie.
	 * @throws SQLException
	 *             Wyjatek.
	 */
	public boolean insertWorkout(int ID_workout, int series, int amount, int id_alltrening, int weight)
			throws SQLException {

		PreparedStatement preparedStatement = null;

		String query = "insert into training values ( ?, ?, ?, ?, ?);";
		try {
			openConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, ID_workout);
			preparedStatement.setInt(2, series);
			preparedStatement.setInt(3, amount);
			preparedStatement.setInt(4, id_alltrening);
			preparedStatement.setInt(5, weight);
			preparedStatement.execute();
		} catch (SQLException e) {
			System.err.println("Blad ");
			e.printStackTrace();
			closeConnection();
			return false;
		}
		closeConnection();
		return true;
	}

	/**
	 * Metoda obslugujaca dodawanie treningu do bazy danych z podanej listy
	 * treningowej.
	 *
	 * @param treningtlist
	 *            Lista zawierajaca elementy treningu.
	 */
	@SuppressWarnings("deprecation")
	public void addTraining(ObservableList<TreningFX> treningtlist) {

		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		ResultSet resultSet = null;

		String query1 = "select MAX(id_traning) from alltraining";
		String query2 = "insert into alltraining values ( ?, ?, ?, null);";

		int day = data.getDate();
		int month = data.getMonth() + 1;
		int ID_username = Baza.user.getId();
		int id = 0;

		try {
			openConnection();
			preparedStatement = connection.prepareStatement(query2);
			preparedStatement.setInt(1, day);
			preparedStatement.setInt(2, month);
			preparedStatement.setInt(3, ID_username);
			preparedStatement.execute();

			preparedStatement2 = connection.prepareStatement(query1);
			resultSet = preparedStatement2.executeQuery();
			while (resultSet.next()) {
				id = resultSet.getInt("MAX(id_traning)");

			}

			closeConnection();
		} catch (Exception e) {
			closeConnection();
		}

		for (int i = 0; i < treningtlist.size(); i++) {

			int ID_workout = treningtlist.get(i).workout.get().getId();
			int series = treningtlist.get(i).series.get();
			int amount = treningtlist.get(i).amount.get();
			int weight = treningtlist.get(i).weight.get();

			try {
				insertWorkout(ID_workout, series, amount, id, weight);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * Metoda sprawdzajaca czy podane parametry funkcji sa zgodne z tymi w bazie
	 * danych.
	 *
	 * @param user
	 *            Nazwa uzytkownika.
	 * @param password
	 *            Haslo uzytkownika.
	 * @return Zwrcaca informacje czy dane sa poprawne.
	 * @throws SQLException
	 *             Wyjatek.
	 */
	public boolean isLogin(String user, String password) throws SQLException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from users where username = ? and password = ?";

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				Baza.user.setId(resultSet.getInt("id"));
				Baza.user.setUsername(resultSet.getString("username"));
				Baza.user.setUrl(resultSet.getString("url"));

				return true;

			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Metoda pobierajaca cwiczenia z bazy danycho okreslonym typie.
	 *
	 * @param type
	 *            Partia pobieranych cwiczen. Je¿eli "All" pobranie wszytskich.
	 * @return Lista cwiczen.
	 */
	public ObservableList<WorkoutFX> getWorkout(String type) {
		if (type == "All") {
			try {
				this.openConnection();
				ResultSet resultSet = null;
				PreparedStatement preparedStatement = null;
				String query = "select * from workout";
				ObservableList<WorkoutFX> data = FXCollections.observableArrayList();
				preparedStatement = connection.prepareStatement(query);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					WorkoutFX w = new WorkoutFX(resultSet.getInt("id"), resultSet.getString("name"),
							resultSet.getString("type"));
					data.add(w);
				}
				this.closeConnection();
				return data;

			} catch (Exception e) {
				this.closeConnection();
				return null;
			}

		} else {
			try {
				this.openConnection();

				ResultSet resultSet = null;
				PreparedStatement preparedStatement = null;
				String query = "select * from workout where type = ?";
				ObservableList<WorkoutFX> data = FXCollections.observableArrayList();
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, type);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					WorkoutFX w = new WorkoutFX(resultSet.getInt("id"), resultSet.getString("name"),
							resultSet.getString("type"));
					data.add(w);
				}
				this.closeConnection();
				return data;
			} catch (Exception e) {
				this.closeConnection();
				return null;
			}
		}
	}

	/**
	 * Metoda pobierajaca typy cwiczen z bazy danych.
	 *
	 * @return Lista dostepnych typow cwiczen.
	 */
	public ObservableList<String> getType() {
		this.openConnection();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		String query = "select DISTINCT type from workout";
		ObservableList<String> data = FXCollections.observableArrayList();
		data.add("All");
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String w = resultSet.getString("type");
				data.add(w);
			}
			this.closeConnection();
			return data;
		} catch (Exception e) {
			this.closeConnection();
			return null;
		}
	}

	/**
	 * Metoda pobierajaca miesiace dostepne w bazie danych.
	 *
	 * @return Lista pobranych miesiecy.
	 */
	public ObservableList<String> getMonth() {
		this.openConnection();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		String query = "select DISTINCT month from alltraining";
		ObservableList<String> data = FXCollections.observableArrayList();
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String w = resultSet.getString("month");
				data.add(w);
			}
			this.closeConnection();
			return data;
		} catch (Exception e) {
			this.closeConnection();
			return null;
		}
	}

	/**
	 * Metoda pobierajaca trening z bazy danych o parametrach podanych w
	 * metodzie. Jezeli type ma wartosc "All" pobrane zostana wszytskie
	 * cwiczenia.
	 *
	 * @param type
	 *            Nazwa parti lub typ cwiczenia. Zalezne od parametru x.
	 * @param x
	 *            Parametrm okreslajacy zapytanie do bazy danych. Jezeli 1
	 *            pobranie po typie. Jezeli 2 pobanie po nazwie cwiczenia.
	 *            Jezeli 3 pobranie po dacie dodania. Jezeli 4 pobranie
	 *            objetosci treningu.
	 * @param day
	 *            Dzien odbycia treningu
	 * @param month
	 *            Miesiac odbycia treningu.
	 * @return Lista treningowa.
	 */
	public ObservableList<AllTreningFX> getTrening(String type, int x, int day, int month) {
		try {
			this.openConnection();
			ResultSet resultSet = null;
			PreparedStatement preparedStatement = null;
			String query = "select W.name, W.type, T.series, T.amount, A.day, A.month, T.weight, T.id"
					+ " FROM training AS T " + "LEFT JOIN alltraining AS A ON A.id_traning = T.id "
					+ "LEFT JOIN workout AS W ON W.id = T.id_workout " + "WHERE A.id_user = " + Baza.user.getId();
			if (type != "All") {
				if (x == 1) {
					query = query + " and type = '" + type + "'";
				} else if (x == 2) {
					query = query + " and name = '" + type + "'";
				} else if (x == 3) {
					query = query + " and day = " + day + " and month = " + month;
				} else if (x == 4) {
					query = "select  A.day, SUM(T.series * T.amount * T.weight)" + " FROM training AS T "
							+ "LEFT JOIN alltraining AS A ON A.id_traning = T.id "
							+ "LEFT JOIN workout AS W ON W.id = T.id_workout " + "WHERE A.id_user = "
							+ Baza.user.getId();
				}
			}
			ObservableList<AllTreningFX> data = FXCollections.observableArrayList();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				AllTreningFX w = new AllTreningFX(resultSet.getString("name"), resultSet.getString("type"),
						resultSet.getInt("amount"), resultSet.getInt("series"), resultSet.getInt("day"),
						resultSet.getInt("month"), resultSet.getInt("weight"), resultSet.getInt("id"));
				data.add(w);
			}
			this.closeConnection();
			return data;
		} catch (Exception e) {
			this.closeConnection();
			return null;
		}
	}

	/**
	 * Metoda pobiera cwiczenie z bazy danych o numerze identyfikacyjnym podanym
	 * przez parametr.
	 *
	 * @param id
	 *            Numer identyfikacyjny cwiczenia.
	 * @return Pobrane cwiczenie.
	 */
	public WorkoutFX getWorkoutone(int id) {
		try {
			this.openConnection();
			WorkoutFX w = null;
			ResultSet resultSet = null;
			PreparedStatement preparedStatement = null;
			String query = "select * from workout WHERE id = " + id;
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				w = new WorkoutFX(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("type"));
			}
			this.closeConnection();
			return w;
		} catch (Exception e) {
			this.closeConnection();
			return null;
		}
	}

	/**
	 * Usuniecie treningu z bazy danych podanego przez parametr.
	 *
	 * @param trening
	 *            Trening usuwany.
	 */
	// TODO Usuniecie szczatkowych danych, ktore pozostaja w bazie. Czy chce je
	// usuwac?
	public void deleteTrening(AllTreningFX trening) {

		try {
			this.openConnection();

			PreparedStatement preparedStatement = null;
			// String query = "DELETE from alltraining AS A, training AS T WHERE
			// T.id = "+trening.getId() + " OR A.id_traning = "
			// +trening.getId();
			String query = "DELETE from training  WHERE id = " + trening.getId();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
			// query = "DELETE from alltraining WHERE id_traning =
			// "+trening.getId();
			// preparedStatement = connection.prepareStatement(query);
			// preparedStatement.execute();
			this.closeConnection();
		} catch (Exception e) {
			this.closeConnection();
		}
	}

	/**
	 * Metoda pobierajaca i zwracajaca dane statystyczne dotyczace treningu.
	 *
	 * @param type
	 *            Partia(typ cwiczenia) rozawazana w statystykach
	 * @param month
	 *            Miesiac wyswietlany.
	 * @return Lista danych w wykresu liniowego.
	 */
	public XYChart.Series<Integer, Integer> getValue(String type, String month) {
		XYChart.Series<Integer, Integer> series = new XYChart.Series<Integer, Integer>();
		try {
			this.openConnection();
			ResultSet resultSet = null;
			PreparedStatement preparedStatement = null;
			String query = null;
			if (type == "All") {
				query = "select  A.day, SUM(T.series * T.amount * T.weight)" + " FROM training AS T "
						+ "LEFT JOIN alltraining AS A ON A.id_traning = T.id "
						+ "LEFT JOIN workout AS W ON W.id = T.id_workout " + "WHERE A.id_user = " + Baza.user.getId()
						+ " and month = " + month + " Group by day ";
			} else {
				query = "select  A.day, SUM(T.series * T.amount * T.weight), W.type FROM training AS T "
						+ " LEFT JOIN alltraining AS A ON A.id_traning = T.id "
						+ " LEFT JOIN workout AS W ON W.id = T.id_workout " + "WHERE A.id_user = " + Baza.user.getId()
						+ " and month = " + month + " AND type =  '" + type + "' " + " Group by day ";
			}
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int value = resultSet.getInt(2);
				int day = resultSet.getInt(1);
				series.getData().add(new XYChart.Data<Integer, Integer>(day, value));
			}
			this.closeConnection();
			series.setName(type);
			return series;
		} catch (Exception e) {
			this.closeConnection();
			return null;
		}
	}

}
