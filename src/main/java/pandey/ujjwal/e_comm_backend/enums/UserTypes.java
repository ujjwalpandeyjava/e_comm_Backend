package pandey.ujjwal.e_comm_backend.enums;

// userName, role, userPassword
public enum UserTypes {
	ADMIN("uAdmin", "ADMIN", "passAdmin"), USER("uUser", "USER", "passUser"), SIMPLE("uSimple", "SIMPLE", "123"),
	HELP("uHelp", "HELP", "123");

	private final String userName;
	private final String role;
	private final String userPassword;

	UserTypes(String userName, String role, String userPassword) {
		this.userName = userName;
		this.role = role;
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public String getRole() {
		return role;
	}

	public String getUserPassword() {
		return userPassword;
	}
}
