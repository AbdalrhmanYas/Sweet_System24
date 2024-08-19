package MySystem;

public class Admin extends User {
	public Admin(String username, String password, String city) {
	    super(username, password, "admin", city);
	}
	public Admin() {
		super();
	}
}
