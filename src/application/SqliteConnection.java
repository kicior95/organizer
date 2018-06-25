package application;

import java.sql.*;
/**
 * Klasa odpowiedzialna za polaczenie sie z baza danych SQLite.
 * @author Kicior
 *
 */
public class SqliteConnection {
/**
 * Polaczenie sie z plikiem bazy danych.
 * @return Czy nast¹pilo polaczenie.
 */
	public static Connection Connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:Baza.sqlite");
			return conn;
		} catch (Exception e) {
			return null;
		}
	}
}
