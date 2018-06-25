package application;

/**
 * Klasa zawierajaca parametry zalogowanego uzytkownika oraz metody z nim
 * zwiazane.
 *
 * @author Kicior
 *
 */
public class User {

	private String username;
	private int id;
	private String url;

	/**
	 * Konstruktor klasy User. Zawiera:
	 *
	 * @param username
	 *            Nazwa uzytkownika
	 * @param id
	 *            Numer ID uzytkownika
	 * @param url
	 *            Adres obrazu uzytkownika.
	 */
	public User(String username, int id, String url) {
		super();
		this.username = username;
		this.id = id;
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
