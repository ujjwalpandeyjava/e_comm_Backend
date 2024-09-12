package pandey.ujjwal.e_comm_backend.entity;

import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class VerificationToken {

	private static final int EXPIRATION_TIME = 20;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
	private User user;
	private String token;
	private Date expirationTime;

	public VerificationToken() {
		super();
	}

	public VerificationToken(String token) {
		this.token = token;
		this.expirationTime = calculateExpirationTime(EXPIRATION_TIME);
	}

	public VerificationToken(User user, String token) {
		this.user = user;
		this.token = token;
		this.expirationTime = calculateExpirationTime(EXPIRATION_TIME);
	}

	private Date calculateExpirationTime(int expirationTime2) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(new Date().getTime());
		calendar.add(Calendar.MINUTE, expirationTime2);
		return new Date(calendar.getTime().getTime());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	@Override
	public String toString() {
		return "VerificationToken {Id:" + Id + ", user:" + user + ", token:" + token + ", expirationTime:"
				+ expirationTime + "}";
	}

}
