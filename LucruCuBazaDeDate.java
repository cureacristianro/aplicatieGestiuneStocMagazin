package aplicatieGestiuneStocMagazin;

import java.sql.*;
import java.util.ArrayList;

public class LucruCuBazaDeDate {

	public static void main(String[] args) {
		obtineListaUtilizatori();

		// testez metodele din clasa inainte de a le apela din alte clase
		// Produs prod = new Produs("bulion", "alimente", 5, 3.22);
		// adaugaProdus(prod);
		// stergeProdus(prod);
		// modificaProdus(prod.getDenumire(), prod);
		// obtineListaProduse();

	}

	/**
	 * obtine conexiune la baza de date
	 * 
	 * @return
	 */
	public static Connection obtineConexiuneLaBazaDeDate() {
		Connection conn = null;
		try {
			// create mysql database connection
			String myDriver = "com.mysql.cj.jdbc.Driver";
			String myUrl = "jdbc:mysql://localhost:3307/p3";
			Class.forName(myDriver);
			conn = DriverManager.getConnection(myUrl, "root", "");
		} catch (Exception e) {
			System.err.println("Nu s-a putut obtine conexiunea la baza de date! \n" + e.getMessage());
		}
		return conn;
	}

	/**
	 * obtine lista de utilizatori din baza de date
	 * 
	 * @return
	 */
	public static ArrayList<User> obtineListaUtilizatori() {
		ArrayList<User> listaUtilizatori = new ArrayList<>();
		try {
			Connection conn = obtineConexiuneLaBazaDeDate();
			String sqlSelectQuery = "SELECT * FROM users";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sqlSelectQuery);
			// iterate through the result set
			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				Boolean isAdmin = rs.getBoolean("isAdmin");
				User newUser = new User(username, password, isAdmin);
				listaUtilizatori.add(newUser);
				// print the results
				System.out.format("%s\n", newUser);
			}
			st.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("Got the following exception: " + e.getMessage());
		}
		return listaUtilizatori;
	}

	/**
	 * obtine lista produse din baza de date
	 * 
	 * @return
	 */
	public static ArrayList<Produs> obtineListaProduse() {
		ArrayList<Produs> listaProduse = new ArrayList<>();
		try {
			Connection conn = obtineConexiuneLaBazaDeDate();
			String sqlSelectQuery = "SELECT * FROM produse";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sqlSelectQuery);
			// iterate through the result set
			while (rs.next()) {
				String denumire = rs.getString("denumire");
				String categorie = rs.getString("categorie");
				int cantitate = rs.getInt("cantitate");
				Double pret = rs.getDouble("pret");
				// print the results
				System.out.format("%s, %s, %s, %s\n", denumire, categorie, cantitate, pret, pret);
				Produs newProduct = new Produs(denumire, categorie, cantitate, pret);
				listaProduse.add(newProduct);
			}
			st.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("Got the following exception: " + e.getMessage());
		}
		return listaProduse;
	}

	/**
	 * adauga produs in baza de date
	 * 
	 * @param produs
	 */
	public static void adaugaProdus(Produs produs) {
		try {
			Connection conn = obtineConexiuneLaBazaDeDate();
			String sqlInsertStatement = "insert into produse (denumire, categorie, cantitate, pret)"
					+ " values (?, ?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(sqlInsertStatement);
			preparedStmt.setString(1, produs.getDenumire());
			preparedStmt.setString(2, produs.getCategorie());
			preparedStmt.setInt(3, produs.getCantitate());
			preparedStmt.setDouble(4, produs.getPret());
			preparedStmt.execute();
			preparedStmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("Got the following exception: " + e.getMessage());
		}
	}

	/**
	 * sterge produs in baza de date
	 * 
	 * @param produs
	 */
	public static void stergeProdus(Produs produs) {
		try {
			Connection conn = obtineConexiuneLaBazaDeDate();
			String sqlDeleteStatement = "delete from produse where denumire = ? ";
			PreparedStatement preparedStmt = conn.prepareStatement(sqlDeleteStatement);
			preparedStmt.setString(1, produs.getDenumire());
			preparedStmt.execute();
			preparedStmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("Got the following exception: " + e.getMessage());
		}
	}

	/**
	 * modifica produs in baza de date
	 * 
	 * @param produs
	 */
	public static void modificaProdus(String numeProdus, Produs produs) {
		try {
			Connection conn = obtineConexiuneLaBazaDeDate();
			String sqlUpdateStatement = "update produse set denumire = ? , categorie = ? , cantitate = ? , pret = ? where denumire = ? ";
			PreparedStatement preparedStmt = conn.prepareStatement(sqlUpdateStatement);
			preparedStmt.setString(1, produs.getDenumire());
			preparedStmt.setString(2, produs.getCategorie());
			preparedStmt.setInt(3, produs.getCantitate());
			preparedStmt.setDouble(4, produs.getPret());
			preparedStmt.setString(5, numeProdus);
			preparedStmt.execute();
			preparedStmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("Got the following exception: " + e.getMessage());
		}
	}

}