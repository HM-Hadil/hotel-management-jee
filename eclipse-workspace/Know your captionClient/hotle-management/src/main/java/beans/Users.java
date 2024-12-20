package beans;

public class Users {
	public int id;
	public  String username;
	public  String password;
	public  String role;
	public  String email;
	public String getUsername() {
		return username;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Users(String username, String password, String role, String email) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
		this.email = email;
	}
	public Users() {
		
	}
	
}
