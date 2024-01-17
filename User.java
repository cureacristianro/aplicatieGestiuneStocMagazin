package aplicatieGestiuneStocMagazin;

public class User {

	private String username;
	private String password;
	private Boolean isAdmin;

	public User(String username, String password, Boolean isAdmin) {
		super();
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		String role = isAdmin ? "admin" : "operator";
		return "Username: " + username + " has role: " + role;
	}

}
